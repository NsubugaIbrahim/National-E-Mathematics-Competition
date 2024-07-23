<?php

namespace App\Imports;

use Illuminate\Support\Collection;
use Maatwebsite\Excel\Concerns\ToCollection;
use App\Models\Question;
use Maatwebsite\Excel\Concerns\WithHeadingRow;

class QuestionImport implements ToCollection, WithHeadingRow
{
    /**
    * @param Collection $collection
    */
    public function collection(Collection $rows)
    {
        foreach ($rows as $row)
        {
            $question = Question::where('questionId', $row['questionid'])->first();
            if($question){
                $question->update([
                'questionText' => $row['questiontext'],
                ]);
            }else{

                Question::create([
                    'questionId' => $row['questionid'],
                'questionText' => $row['questiontext'],
                ]);
            }

        }
    }
}
