<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use PDO; //import PDO

class RepresentativeController extends Controller
{
    public function __construct()
    {
        $this->middleware('auth');      
    }
    
    public function store(Request $request) {
        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
            // Collect form data
            $representativeName = $_POST['name'];
            $representativeEmail = $_POST['email']; 
            $schoolRegNo = $_POST['regno'];
            
            // Database connection
            $pdo = new PDO('mysql:host=localhost;dbname=laravel', 'root', '');
        
            // SQL query
            $sql = "INSERT INTO representatives(representativeName, representativeEmail, schoolRegNo) VALUES (?, ?, ?)";
            $stmt = $pdo->prepare($sql);
            $stmt->execute(array($representativeName, $representativeEmail, $schoolRegNo));
           
             // Redirect to the success_page route
             return redirect()->away(' http://localhost:8000/success_reps_submit.php');   
        }
    }
}
