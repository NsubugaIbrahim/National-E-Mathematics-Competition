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


        $schoolRankings = DB::table('results')
            ->join('participants', 'results.participantId', '=', 'participants.participantId')
            ->select('participants.schoolRegno', DB::raw('SUM(results.correctAnswers) as total_correct'))
            ->groupBy('participants.schoolRegno')
            ->orderBy('total_correct', 'desc')
            ->get();

        $performanceOverTime = DB::table('results')
            ->join('participants', 'results.participantId', '=', 'participants.participantId')
            ->select(
                DB::raw('YEAR(results.receivedAt) as year'),
                'participants.schoolRegno',
                'participants.username',
                DB::raw('SUM(results.correctAnswers) as total_correct')
            )
            ->groupBy('year', 'participants.schoolRegno', 'participants.username')
            ->orderBy('year', 'asc')
            ->get();  
            
        
            $participantId = 1; // Example participantId

        // Calculate total number of repeated attempts by the participant (n)
        $totalRepeatedAttempts = DB::table('attempts')
            ->where('participantId', $participantId)
            ->groupBy('questionId')
            ->havingRaw('COUNT(*) > 1')
            ->select(DB::raw('COUNT(*) as repeated_attempts'))
            ->get()
            ->sum('repeated_attempts');

        // Calculate attempts for each question by the participant and compute percentages (i)
        $repetition = DB::table('attempts')
            ->select('questionId', DB::raw('COUNT(*) as total_attempts'), DB::raw('SUM(isCorrect) as correct_attempts'))
            ->where('participantId', $participantId)
            ->groupBy('questionId')
            ->having('total_attempts', '>', 1)
            ->get();

        $repetition->each(function ($item) use ($totalRepeatedAttempts) {
            $item->repetition_percentage = ($item->total_attempts / $totalRepeatedAttempts) * 100;
        });
        


        $challengeId = 1; // Example challengeId
        $worstPerformingSchools = DB::table('results')
            ->join('participants', 'results.participantId', '=', 'participants.participantId')
            ->select('participants.schoolRegno', DB::raw('SUM(results.correctAnswers) as total_correct'))
            ->where('results.challengeId', $challengeId)
            ->groupBy('participants.schoolRegno')
            ->orderBy('total_correct', 'asc')
            ->limit(10)
            ->get();
      

            $bestPerformingSchools = DB::table('results')
                ->join('participants', 'results.participantId', '=', 'participants.participantId')
                ->select('participants.schoolRegno', DB::raw('SUM(results.correctAnswers) as total_correct'))
                ->groupBy('participants.schoolRegno')
                ->orderBy('total_correct', 'desc')
                ->limit(10)
                ->get();


        $incompleteChallenges = DB::table('participants')
                ->leftJoin('attempts', 'participants.participantId', '=', 'attempts.participantId')
                ->select('participants.participantId', 'participants.username', DB::raw('COUNT(attempts.attemptId) as total_attempts'))
                ->groupBy('participants.participantId', 'participants.username')
                ->having('total_attempts', '<', DB::raw('(SELECT COUNT(questionId) FROM questions)'))
                ->get();

                return view('pages.analytics', compact(
                    'mostCorrectlyAnsweredQuestions',
                    'schoolRankings',
                    'performanceOverTime',
                    'repetition',
                    'worstPerformingSchools',
                    'bestPerformingSchools',
                    'incompleteChallenges'
                ));
    }
}
