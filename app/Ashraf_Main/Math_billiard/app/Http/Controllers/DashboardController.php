<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class DashboardController extends Controller
{
    public function dashboard()
    {
        if (Auth::user()->user_type == 1) {
            return view('admin.dashboard');
        } elseif (Auth::user()->user_type == 2) {
            return view('school_reps.dashboard');
        } elseif (Auth::user()->user_type == 3) {
            return view('participants.dashboard');
        }

        // Optionally, handle cases where the user_type doesn't match any of the specified values
        return redirect('/'); // Redirect to home or any other default page
    }   
}
