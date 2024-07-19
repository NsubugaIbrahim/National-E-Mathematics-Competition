<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Attempt;
use Illuminate\Support\Facades\DB;

class AnalyticsController extends Controller
{
    public function analytics()
    {
        $mostCorrectlyAnsweredQuestions = Attempt::select('questionId', DB::raw('COUNT(*) as correct_answers'))
            ->where('isCorrect', 1)
            ->groupBy('questionId')
            ->orderBy('correct_answers', 'desc')
            ->limit(10)
            ->get();

        return view('pages.analytics', compact('mostCorrectlyAnsweredQuestions'));

        $schoolRankings = DB::table('results')
            ->join('participants', 'results.participantId', '=', 'participants.participantId')
            ->select('participants.schoolRegno', DB::raw('SUM(results.correctAnswers) as total_correct'))
            ->groupBy('participants.schoolRegno')
            ->orderBy('total_correct', 'desc')
            ->get();
    }
}
