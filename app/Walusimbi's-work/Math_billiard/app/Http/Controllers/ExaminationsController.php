<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Models\ExamModel;
use Illuminate\Support\Facades\Validator;
use Maatwebsite\Excel\Facades\Excel;
use App\Imports\QuestionsImport;
use App\Imports\AnswersImport;
// use App\Models\ClassModel;

class ExaminationsController extends Controller
{
    public function exam_list()
    {
        $data['getRecord'] = ExamModel::getRecord();
        $data['header_title'] = "Exam List";
        return view('admin.exam.list', $data);
    }
    
    public function exam_add()
    {
        $data['header_title'] = "Add New Exam";
        return view('admin.exam.add', $data);
    }

    public function exam_insert(Request $request)
    {
        $request->validate([
            'name' => 'required|string|max:255',
            'note' => 'nullable|string|max:1000',
        ]);

        $exam = new ExamModel;
        $exam->name = trim($request->name);
        $exam->note = trim($request->note);
        $exam->created_by = Auth::user()->id;
        $exam->save();

        return redirect('admin/exam/list')->with('success', "Exam successfully created");
    }

    public function exam_edit($id)
    {
        $data['getRecord'] = ExamModel::getSingle($id);
        if(!empty($data['getRecord']))
        {
            $data['header_title'] = "Edit Exam";
            return view('admin.exam.edit', $data);
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
            'note' => 'nullable|string|max:1000',
        ]);

        $exam = ExamModel::getSingle($id);
        $exam->name = trim($request->name);
        $exam->note = trim($request->note);
        $exam->save();

        return redirect('admin/exam/list')->with('success', "Exam successfully updated");
    }
    
    public function exam_delete($id)
    {
        $getRecord = ExamModel::getSingle($id);
        if(!empty($getRecord))
        {
            $getRecord->is_delete = 1;
            $getRecord->save();

            return redirect()->back()->with('success', "Exam successfully deleted");
        }
        else
        {
            abort(404);
        }
    }

   public function exam_schedule(Request $request)
   {
        // $data['getClass'] = ClassModel::getClass();
        $data['getExam'] = ExamModel::getExam();

        $result = array();
        if(!empty($request->get('exam_id')))
        {
            
        }

        $data['header_title'] = "Exam Schedule";
        return view('admin.exam.exam_schedule', $data);
   }

   
}
