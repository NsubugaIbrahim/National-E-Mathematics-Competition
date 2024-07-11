<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Imports\AnswerImport;
use Maatwebsite\Excel\Facades\Excel;

class AnswerController extends Controller
{
    public function importExcelAnswer(Request $request)
  {
    $request->validate([
      'import_answers_file' => [
        'required',
        'file'
      ],
    ]);

    Excel::import(new AnswerImport, $request->file('import_answers_file'));
    return redirect()->back()->with('status', 'Answers Excel Document Imported successfully');
  }
}
