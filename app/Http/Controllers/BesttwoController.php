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
            ->select('participantId', DB::raw('AVG(score) as average_score'))
            ->groupBy('participantId')
            ->orderBy('average_score', 'desc')
            ->limit(2)
            ->get();

        return view('top_participants', compact('topParticipants'));
    }
}
