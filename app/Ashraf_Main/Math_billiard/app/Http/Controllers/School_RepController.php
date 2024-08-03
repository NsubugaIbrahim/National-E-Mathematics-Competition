<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Models\SchoolRepModel;
use Illuminate\Support\Facades\Validator;
use Maatwebsite\Excel\Facades\Excel;
use App\Imports\QuestionsImport;
use App\Imports\AnswersImport;
// use App\Models\ClassModel;

class School_RepController extends Controller
{
    public function exam_list()
    {
        $data['getRecord'] = SchoolRepModel::getRecord();
        $data['header_title'] = "School Rep List";
        return view('admin.school_rep.list', $data);
    }
    
    public function exam_add()
    {
        $data['header_title'] = "Add New Representative";
        return view('admin.school_rep.add', $data);
    }

    public function exam_insert(Request $request)
    {
        $request->validate([
            'representative_name' => 'nullable|string|max:1000',
            'representative_email' => 'required|string|max:1000',
            'school_regNo' => 'required|string|max:255',
        ]);

        $exam = new SchoolRepModel;
        $exam->representative_name = trim($request->representative_name);
        $exam->representative_email = trim($request->representative_email);
        $exam->school_regNo = trim($request->school_regNo);
        $exam->created_by = Auth::user()->id;
        $exam->save();

        return redirect('admin/school_rep/list')->with('success', "School Representative successfully created");
    }

    public function exam_edit($id)
    {
        $data['getRecord'] = SchoolRepModel::getSingle($id);
        if(!empty($data['getRecord']))
        {
            $data['header_title'] = "Edit School";
            return view('admin.school_rep.edit', $data);
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
            'representative_name' => 'nullable|string|max:1000',
            'representative_email' => 'required|string|max:1000',
        ]);

        $exam = SchoolRepModel::getSingle($id);
        $exam->representative_name = trim($request->representative_name);
        $exam->representative_email = trim($request->representative_email);
        $exam->school_regNo = trim($request->school_regNo);
        $exam->save();

        return redirect('admin/school_rep/list')->with('success', "School successfully updated");
    }
    
    public function exam_delete($id)
    {
        $getRecord = SchoolRepModel::getSingle($id);
        if (!empty($getRecord)) {
            $getRecord->is_delete = 1;
            $getRecord->save();

            return redirect()->back()->with('success', "School Representative successfully deleted");
        } else {
            abort(404);
        }
    }
   
}
