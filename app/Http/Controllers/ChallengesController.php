<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Challenge;

class ChallengesController extends Controller
{
    public function __construct()
    {
        $this->middleware('auth');      
    }
    
    public function store(Request $request)
    {
        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
            // Collect form data
            $numberOfQuestions = $_POST['no_questions'] ?? 'default_value';
            $duration = $_POST['duration'];
            $startDate = $_POST['start'];
            $endDate = $_POST['end'];
    
            // Create a new Challenge instance and set the attributes
            $challenge = new Challenge();
            $challenge->numberOfQuestions = $numberOfQuestions;
            $challenge->duration = $duration;
            $challenge->startDate = $startDate;
            $challenge->endDate = $endDate;
            $challenge->save(); // Save the model to the database
    
            // Redirect to the success_page route
            return redirect()->back()->with('status', 'Challenge has been successfully created and uploaded to the database');
        }
    }

    public function searchChallenges(Request $request)
        {
          $searchTerm = $request->input('search');

          $challenges = Challenge::where('challengeName', 'like', "%{$searchTerm}%")
                                  ->orWhere('numberOfQuestions', $searchTerm)
                                  ->get(); // Adjust search criteria as needed

          return view('admin.challenges.viewchallenges', compact('challenges'));
        }
}
