<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Models\ParticipantsModel;
use Illuminate\Support\Facades\Validator;
use Maatwebsite\Excel\Facades\Excel;
use App\Imports\QuestionsImport;
use App\Imports\AnswersImport;
// use App\Models\ClassModel;

class ParticipantsController extends Controller
{
    public function exam_list()
    {
        $data['getRecord'] = ParticipantsModel::getRecord();
        $data['header_title'] = "Participants List";
        return view('admin.participants.list', $data);
    }
    
    public function exam_add()
    {
        $data['header_title'] = "Add New Participants";
        return view('admin.participants.add', $data);
    }

    public function exam_insert(Request $request)
    {
        $request->validate([
            'username' => 'required|string|max:255',
            'firstName' => 'required|string|max:255',
            'lastName' => 'required|string|max:255',
            'email' => 'nullable|string|email|max:255',
            'dateOfBirth' => 'nullable|date',
            'school_regNo' => 'required|string|max:255',
            'status' => 'nullable|string|max:255',
        ]);

        $exam = new ParticipantsModel;
        $exam->username = trim($request->username);
        $exam->firstName = trim($request->firstName);
        $exam->lastName = trim($request->lastName);
        $exam->email = trim($request->email);
        $exam->dateOfBirth = trim($request->dateOfBirth);
        $exam->school_regNo = trim($request->school_regNo);
        $exam->status = trim($request->status);
        $exam->save();

        return redirect('admin/participants/list')->with('success', "Participant successfully created");
    }

    public function exam_edit($id)
    {
        $data['getRecord'] = ParticipantsModel::getSingle($id);
        if(!empty($data['getRecord']))
        {
            $data['header_title'] = "Edit Participant";
            return view('admin.participants.edit', $data);
        }
        else
        {
            abort(404);
        }
    }

    public function exam_update($id, Request $request)
    {
        $request->validate([
            'username' => 'required|string|max:255',
            'firstName' => 'required|string|max:255',
            'lastName' => 'required|string|max:255',
            'email' => 'nullable|string|email|max:255',
            'dateOfBirth' => 'nullable|date',
            'school_regNo' => 'required|string|max:255',
            'status' => 'nullable|string|max:255',
        ]);

        $exam = ParticipantsModel::getSingle($id);
        $exam->username = trim($request->username);
        $exam->firstName = trim($request->firstName);
        $exam->lastName = trim($request->lastName);
        $exam->email = trim($request->email);
        $exam->dateOfBirth = trim($request->dateOfBirth);
        $exam->school_regNo = trim($request->school_regNo);
        $exam->status = trim($request->status);
        $exam->save();

        return redirect('admin/participants/list')->with('success', "Participant successfully updated");
    }
    
    public function exam_delete($id)
    {
        $getRecord = ParticipantsModel::getSingle($id);
        if (!empty($getRecord)) {
            $getRecord->is_delete = 1;
            $getRecord->save();

            return redirect()->back()->with('success', "Participant successfully deleted");
        } else {
            abort(404);
        }
    }
   
}
