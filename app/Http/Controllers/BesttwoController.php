<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class BesttwoController extends Controller
{
    public function getTopParticipants()
    {
        // Fetch the top 2 participants with the highest average scores across their attempted challenges
        $topParticipants = DB::table('results')
            ->join('participants', 'results.participantId', '=', 'participants.participantId')
            ->join('schools', 'participants.schoolRegno', '=', 'schools.schoolRegNo')
            ->select('participants.username', 'schools.schoolName', 'results.participantId', DB::raw('AVG(results.score) as average_score'))
            ->groupBy('results.participantId', 'participants.username', 'schools.schoolName')
            ->orderBy('average_score', 'desc')
            ->limit(2)
            ->get();

        return view('top_participants', compact('topParticipants'));
    }
}
