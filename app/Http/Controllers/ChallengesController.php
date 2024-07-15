<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use PDO; //import PDO

class ChallengesController extends Controller
{
    public function __construct()
    {
        $this->middleware('auth');
    }

    public function store(Request $request) {
        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
            // Collect form data
            $numberOfQuestions = $_POST['no_questions'] ?? 'default_value'; //null coalescing operator ?? to check if the ‘no_questions’ key exists before accessing it
            $duration = $_POST['duration'];
            $startDate = $_POST['start'];
            $endDate = $_POST['end'];

            // Database connection
            $pdo = new PDO('mysql:host=localhost;dbname=maths', 'root', '');

            // SQL query
            $sql = "INSERT INTO challenges(numberOfQuestions, duration, startDate, endDate ) VALUES (?, ?, ?, ?)";
            $stmt = $pdo->prepare($sql);
            $stmt->execute(array($numberOfQuestions, $duration, $startDate, $endDate));

            // Redirect to the success_page route
            // return redirect()->away('http://localhost:8000/success_page.php');
            return redirect()->back()->with('status', 'Challenge has been successfully created and uploaded to the database');
        }
    }
}
