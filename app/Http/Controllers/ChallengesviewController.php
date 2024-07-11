<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\challenge;


class ChallengesviewController extends Controller
{
    
    public function viewchallenges()
    {
        $arr['challenges'] = challenge::all(); // Fetch data from your MySQL table
        return view('admin.challenges.viewchallenges')->with($arr);
        dd($arr);
    }
}
