<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use App\Models\School;

class SchoolsController extends Controller
{
    public function __construct()
    {
        $this->middleware('auth');      
    }
    
    public function store(Request $request)
{
    // Access form data
    $schoolRegNo = $request->input('regno');
    $schoolName = $request->input('name');
    $district = $request->input('district');
    
    // Loop through each row's data and create a new School instance
    for ($i = 0; $i < count($schoolRegNo); $i++) {
        School::create([
            'schoolRegNo' => $schoolRegNo[$i],
            'schoolName' => $schoolName[$i],
            'district' => $district[$i],
        ]);
    }
    
    // Redirect to the success page
    return redirect()->back()->with('status', 'School(s) successfully registered');
}

}
