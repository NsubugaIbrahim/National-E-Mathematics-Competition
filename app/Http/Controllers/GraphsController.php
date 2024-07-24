<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class GraphsController extends Controller
{
    public function index()
    {
        $schools = DB::table('schools')
            ->select(DB::raw('YEARWEEK(created_at, 1) as week'), DB::raw('COUNT(*) as count'))
            ->groupBy('week')
            ->orderBy('week')
            ->get();

        $participants = DB::table('participants')
            ->select(DB::raw('YEARWEEK(created_at, 1) as week'), DB::raw('COUNT(*) as count'))
            ->groupBy('week')
            ->orderBy('week')
            ->get();

            

        return view('dashboard', compact('schools', 'participants'));
    }
}
