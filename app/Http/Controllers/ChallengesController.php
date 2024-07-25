<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use PDO;
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
            
            // Database connection
            $pdo = new PDO('mysql:host=localhost;dbname=mathchallenge', 'root', '');
        
            // SQL query
            $sql = "INSERT INTO challenges(numberOfQuestions, duration, startDate, endDate ) VALUES (?, ?, ?, ?)";
            $stmt = $pdo->prepare($sql);
            $stmt->execute(array($numberOfQuestions, $duration, $startDate, $endDate));
           
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
