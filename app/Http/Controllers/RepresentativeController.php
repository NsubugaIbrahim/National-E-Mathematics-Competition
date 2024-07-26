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
            // Access form data as arrays
            $representativeName = $request->input('name');
            $representativeEmail = $request->input('email');
            $schoolRegNo = $request->input('regno');
            $password = $request->input('password');

            // Loop through each row's data and insert into database
            for ($i = 0; $i < count($representativeName); $i++) {
              $pdo = new PDO('mysql:host=localhost;dbname=mathchallenge', 'root', '');
              $sql = "INSERT INTO representatives(representativeName, representativeEmail, schoolRegNo, password) VALUES (?, ?, ?, ?)";
              $stmt = $pdo->prepare($sql);
              $stmt->execute(array($representativeName[$i], $representativeEmail[$i], $schoolRegNo[$i], $password[$i]));
            }

            // Redirect to the success page
            return redirect()->back()->with('status', 'Representative(s) successfully validated');
        }
    }
}
