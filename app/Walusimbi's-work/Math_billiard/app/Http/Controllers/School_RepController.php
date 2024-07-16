<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\User;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Str;
use Auth;
use Carbon\Carbon;

class School_RepController extends Controller
{
    public function list()
    {
        $data['getRecord'] = User::getSchool_Rep();
        $data['header_title'] = "School Representatives List";
        return view('admin.school_rep.list', $data);
    }

    public function add()
    {
        $data['header_title'] = "Add New Representative";
        return view('admin.school_rep.add', $data);
    }


    public function insert(Request $request)
    {
         // Validate unique email, ignoring the current user's email
         $request->validate([
            'email' => 'required|email|max:255|unique:users,email,' . $request->id,
            'address' => 'max:255',
        ]);

        $student = new User;
        $student->name = trim($request->name);
        $student->last_name = trim($request->last_name);
      
       
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

              
        $student->email = trim($request->email);
        $student->password = Hash::make($request->password);
        $student->address = trim($request->address);
        $student->user_type = 2;
        $student->save();

        return redirect('admin/school_rep/list')->with('success', "School representative successfully created");
    }

    public function edit($id)
    {
        $data['getRecord'] = User::getSingle($id);
        if (!empty($data['getRecord'])) 
        {                       
            $data['header_title'] = "Edit Representative";
            return view('admin.school_rep.edit', $data);
        } else {
            abort(404);
        }
    }

    public function update($id, Request $request)
    {
        $request->validate([
            'email' => 'required|email|max:255|unique:users,email,' . $request->id,
            'address' => 'max:255',
        ]);

        $student = User::getSingle($id);

        $student->name = trim($request->name);
        $student->last_name = trim($request->last_name);
        $student->email = trim($request->email);
      
       
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

              
        if (!empty($request->password)) {
            $student->password = Hash::make($request->password);
        }

        $student->save();

        return redirect('admin/school_rep/list')->with('success', "{$student->name} is successfully updated");
    }

    public function delete($id)
    {
        $user = User::getSingle($id);
        $user->is_delete = 1;
        $user->save();

        return redirect('admin/school_rep/list')->with('success', "{$user->name} has been successfully deleted");
    }
}
