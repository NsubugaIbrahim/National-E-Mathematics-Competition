<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\representative;


class RepresentativesviewController extends Controller
{
    public function viewrepresentatives() {
        $arr['representatives'] = Representative::all(); // Fetch data
        return view('admin.representatives.viewrepresentatives')->with($arr);
    }
}
