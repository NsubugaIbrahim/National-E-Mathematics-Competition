<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\school;


class SchoolsviewController extends Controller
{
    public function viewschools() {
        $arr['schools'] = School::all(); // Fetch data
        return view('admin.schools.viewschools')->with($arr);
    }
}
