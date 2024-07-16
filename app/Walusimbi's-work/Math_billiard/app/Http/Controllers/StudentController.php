<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\User;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Str;
use Auth;
use Carbon\Carbon;

class StudentController extends Controller
{
    public function list()
    {
        $data['getRecord'] = User::getStudent();
        $data['header_title'] = "Student List";
        return view('admin.student.list', $data);
    }

    public function add()
    {
        $data['header_title'] = "Add New Student";
        return view('admin.student.add', $data);
    }

    public function insert(Request $request)
    {
        // Validate unique email, ignoring the current user's email
        $request->validate([
            'email' => 'required|email|max:255|unique:users,email,' . $request->id,
            'religion' => 'max:20',
        ]);

        $student = new User;
        $student->name = trim($request->name);
        $student->last_name = trim($request->last_name);
        $student->admission_number = trim($request->admission_number);
        $student->gender = trim($request->gender);

        if (!empty($request->date_of_birth)) {
            $student->date_of_birth = trim($request->date_of_birth);
        }

        if (!empty($request->file('profile_pic'))) {
            if (!empty($student->getProfile())) {
                unlink('upload/profile/' . $student->profile_pic);
            }
            $ext = $request->file('profile_pic')->getClientOriginalExtension();
            $file = $request->file('profile_pic');
            $randomStr = date('ymdhis') . Str::random(20);
            $filename = strtolower($randomStr) . '.' . $ext;
            $file->move('upload/profile/', $filename);
            $student->profile_pic = $filename;
        }

        $student->religion = trim($request->religion);

        if (!empty($request->admission_date)) {
            $student->admission_date = trim($request->admission_date);
        }

        $student->status = trim($request->status);
        $student->email = trim($request->email);
        $student->password = Hash::make($request->password);
        $student->user_type = 3;
        $student->save();

        return redirect('admin/student/list')->with('success', "Student successfully created");
    }

    public function edit($id)
    {
        $getRecord = User::getSingle($id);
        if ($getRecord) {
            $data['getStudent'] = User::getStudent();
            $data['getRecord'] = $getRecord;
            $data['header_title'] = "Edit Student";
            return view('admin.student.edit', $data);
        } else {
            abort(404);
        }
    }

    public function update($id, Request $request)
    {
        $request->validate([
            'email' => 'required|email|max:255|unique:users,email,' . $id,
            'religion' => 'max:20',
        ]);

        $student = User::getSingle($id);
        $student->name = trim($request->name);
        $student->last_name = trim($request->last_name);
        $student->admission_number = trim($request->admission_number);
        $student->gender = trim($request->gender);

        if (!empty($request->date_of_birth)) {
            $student->date_of_birth = trim($request->date_of_birth);
        }

        if (!empty($request->file('profile_pic'))) {
            if (!empty($student->getProfile())) {
                unlink('upload/profile/' . $student->profile_pic);
            }
            $ext = $request->file('profile_pic')->getClientOriginalExtension();
            $file = $request->file('profile_pic');
            $randomStr = date('ymdhis') . Str::random(20);
            $filename = strtolower($randomStr) . '.' . $ext;
            $file->move('upload/profile/', $filename);
            $student->profile_pic = $filename;
        }

        $student->religion = trim($request->religion);

        if (!empty($request->admission_date)) {
            $student->admission_date = trim($request->admission_date);
        }

        $student->status = trim($request->status);
        $student->email = trim($request->email);
        if (!empty($request->password)) {
            $student->password = Hash::make($request->password);
        }

        $student->save();

        return redirect('admin/student/list')->with('success', "{$student->name} is successfully updated");
    }

    public function analytics()
    {
        $totalStudents = User::where('user_type', 3)->count();

        $weeklyData = User::selectRaw('COUNT(*) as count, WEEK(created_at) as week')
            ->where('user_type', 3)
            ->where('created_at', '>=', Carbon::now()->subWeeks(7))
            ->groupBy('week')
            ->pluck('count', 'week');

        $weeklyCounts = [];
        $currentWeek = Carbon::now()->week;

        for ($i = 0; $i < 7; $i++) {
            $week = ($currentWeek - $i <= 0) ? $currentWeek - $i + 52 : $currentWeek - $i;
            $weeklyCounts[] = $weeklyData->get($week, 0);
        }

        return view('admin.admin.analytics', compact('totalStudents', 'weeklyCounts'));
    }

    public function delete($id)
    {
        $user = User::getSingle($id);
        $user->is_delete = 1;
        $user->save();

        return redirect('admin/student/list')->with('success', "{$user->name} has been successfully deleted");
    }
}
