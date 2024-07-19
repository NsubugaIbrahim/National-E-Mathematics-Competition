<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Models\SchoolModel;
use Illuminate\Support\Facades\Validator;
use Maatwebsite\Excel\Facades\Excel;
use App\Imports\QuestionsImport;
use App\Imports\AnswersImport;
// use App\Models\ClassModel;

class SchoolController extends Controller
{
    public function exam_list()
    {
        $data['getRecord'] = SchoolModel::getRecord();
        $data['header_title'] = "School List";
        return view('admin.school.list', $data);
    }
    
    public function exam_add()
    {
        $data['header_title'] = "Add New School";
        return view('admin.school.add', $data);
    }

    public function exam_insert(Request $request)
    {
        $request->validate([
            'name' => 'required|string|max:255',
            'district' => 'nullable|string|max:1000',
            'school_regNo' => 'required|string|max:255',
            'representative_name' => 'nullable|string|max:1000',
            'representative_email' => 'required|string|max:1000',
        ]);

        $exam = new SchoolModel;
        $exam->name = trim($request->name);
        $exam->district = trim($request->district);
        $exam->school_regNo = trim($request->school_regNo);
        $exam->representative_name = trim($request->representative_name);
        $exam->representative_email = trim($request->representative_email);
        $exam->created_by = Auth::user()->id;
        $exam->save();

        return redirect('admin/school/list')->with('success', "School successfully created");
    }

    public function exam_edit($id)
    {
        $data['getRecord'] = SchoolModel::getSingle($id);
        if(!empty($data['getRecord']))
        {
            $data['header_title'] = "Edit School";
            return view('admin.school.edit', $data);
        }
        else
        {
            abort(404);
        }
    }

    public function exam_update($id, Request $request)
    {
        $request->validate([
            'name' => 'required|string|max:255',
            'district' => 'nullable|string|max:1000',
            'school_regNo' => 'required|string|max:255',
            'representative_name' => 'nullable|string|max:1000',
            'representative_email' => 'required|string|max:1000',
        ]);

        $exam = SchoolModel::getSingle($id);
        $exam->name = trim($request->name);
        $exam->district = trim($request->district);
        $exam->school_regNo = trim($request->school_regNo);
        $exam->representative_name = trim($request->representative_name);
        $exam->representative_email = trim($request->representative_email);
        $exam->save();

        return redirect('admin/school/list')->with('success', "School successfully updated");
    }
    
    public function exam_delete($id)
    {
        $getRecord = SchoolModel::getSingle($id);
        if (!empty($getRecord)) {
            $getRecord->is_delete = 1;
            $getRecord->save();

            return redirect()->back()->with('success', "School successfully deleted");
        } else {
            abort(404);
        }
    }
   
}
