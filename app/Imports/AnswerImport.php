<?php

namespace App\Imports;

use Illuminate\Support\Collection;
use Maatwebsite\Excel\Concerns\ToCollection;
use App\Models\Answer;
use Maatwebsite\Excel\Concerns\WithHeadingRow;

class AnswerImport implements ToCollection, WithHeadingRow
{
    /**
    * @param Collection $collection
    */
    public function collection(Collection $rows)
    {
        foreach ($rows as $row) 
        {
            $answer = Answer::where('answerid', $row['answerid'])->first();
            if($answer){
                $answer->update([
                'questionId' => $row['questionid'],
                    'answer' => $row['answer'],
                    'marks' => $row['marks'],
                ]); 
            }else{

                Answer::create([
                    'answerId' => $row['answerid'],
                    'questionId' => $row['questionid'],
                    'answer' => $row['answer'],
                    'marks' => $row['marks'],
                ]);   
            }
        
        }
    }
}
