<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Attempt;

class AttemptController extends Controller
{
    public function store(Request $request)
    {
        $attempt = new Attempt();
        $attempt->participant_id = $request->input('participantId');
        $attempt->challenge_id = $request->input('challengeId');
        $attempt->question_id = $request->input('questionId');
        $attempt->is_correct = $request->input('isCorrect');
        $attempt->timestamp = $request->input('timestamp');
        $attempt->save();

        return response()->json(['message' => 'Attempt saved successfully'], 200);
    }
}

