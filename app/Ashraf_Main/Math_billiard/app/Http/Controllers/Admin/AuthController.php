<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Models\User;

class AuthController extends Controller
{
    public function postLogin(Request $request)
{
    $request->validate([
        'email' => 'required|email',
        'password' => 'required',
    ]);

    $credentials = $request->only('email', 'password');

    if (Auth::attempt($credentials)) {
        // Authentication passed...
        $user = Auth::user();
        if ($user->user_type == 1) {
            return redirect()->intended('admin/dashboard')
                ->with('success', 'Welcome back, Admin!');
        } elseif ($user->user_type == 2) {
            return redirect()->intended('school_reps/dashboard')
                ->with('success', 'Welcome back, School Representative!');
        } elseif ($user->user_type == 3) {
            return redirect()->intended('participants/dashboard')
                ->with('success', 'Welcome back, Participant!');
        }
    }

    return redirect()->back()->withInput($request->only('email'))
        ->with('error', 'Invalid login credentials');
}

    public function continueToDashboard(Request $request)
    {
        // Handle any necessary logic before redirecting
        return redirect()->route('admin.dashboard'); // Replace 'admin.dashboard' with your actual route name
    }

    public function forgotPasswordForm()
    {
        return view('auth.forgot');
    }

    public function postForgotPassword(Request $request)
    {
        $request->validate([
            'email' => 'required|email',
        ]);

        $user = User::where('email', $request->email)->first();

        if ($user) {
            // Logic for sending password reset email or handling forgot password flow
            return redirect()->back()->with('success', 'Password reset instructions have been sent to your email.');
        } else {
            return redirect()->back()->with('error', 'Email not found in the system.');
        }
    }

    public function logout()
    {
        Auth::logout();
        return redirect('/');
    }
}
