<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use PDO; //import PDO

class SchoolsController extends Controller
{
    public function __construct()
    {
        $this->middleware('auth');      
    }
    
    public function store(Request $request) {
        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
            // Collect form data
            $schoolRegNo = $_POST['regno'];
            $name = $_POST['name'];
            $district = $_POST['district']; 
        
            // Database connection
            $pdo = new PDO('mysql:host=localhost;dbname=laravel', 'root', '');
        
            // SQL query
            $sql = "INSERT INTO schools(schoolRegNo, name, district) VALUES (?, ?, ?)";
            $stmt = $pdo->prepare($sql);
            $stmt->execute(array($schoolRegNo, $name, $district));
           
             // Redirect to the success_page route
             return redirect()->away('http://localhost:8000/success_schools_submit.php');   
        }
    }
}
