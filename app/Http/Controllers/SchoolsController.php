<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use PDO; //import PDO

class SchoolsController extends Controller
{
    public function __construct()
    {
        $this->middleware('auth');
    }

    public function store(Request $request) {
        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
          // Access form data as arrays
            $schoolRegNo = $request->input('regno');
            $schoolName = $request->input('name');
            $district = $request->input('district');

          // Loop through each row's data and insert into database
          for ($i = 0; $i < count($schoolRegNo); $i++) {
            $pdo = new PDO('mysql:host=localhost;dbname=mathchallenge', 'root', '');
            $sql = "INSERT INTO schools(schoolRegNo, schoolName, district) VALUES (?, ?, ?)";
            $stmt = $pdo->prepare($sql);
            $stmt->execute(array($schoolRegNo[$i], $schoolName[$i], $district[$i]));
          }

          // Redirect to the success page
          return redirect()->back()->with('status', 'School(s) successfully registered');
        }
      }

}
