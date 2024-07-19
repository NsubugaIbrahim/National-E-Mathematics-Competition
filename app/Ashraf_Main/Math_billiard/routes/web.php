<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Admin\AuthController;
use App\Http\Controllers\DashboardController;
use App\Http\Controllers\AdminController;
use App\Http\Controllers\AnalyticsController;
use App\Http\Controllers\StudentController;
use App\Http\Controllers\SchoolController;
use App\Http\Controllers\School_RepController;

Route::get('/', function () {
    return view('auth.login');
});

Route::get('/login', [AuthController::class, 'showLoginForm'])->name('login');
Route::post('/login', [AuthController::class, 'postLogin'])->name('postLogin');
Route::get('/forgot-password', [AuthController::class, 'forgotPasswordForm'])->name('forgot-password');
Route::post('/forgot-password', [AuthController::class, 'postForgotPassword'])->name('forgot-password.post');

Route::post('/continue', [AuthController::class, 'continueToDashboard'])->name('continue');
Route::get('/logout', [AuthController::class, 'logout'])->name('logout');

Route::get('/admin/admin/list', [AdminController::class, 'list']);

Route::get('/admin/admin/analytics', [AnalyticsController::class, 'analytics']);


// Define routes for different user types with their respective middleware
Route::group(['middleware' => 'admin'], function() {
    Route::get('/admin/dashboard', [DashboardController::class, 'dashboard'])->name('admin.dashboard'); 

    // Admin routes
    Route::get('/admin/admin/list', [AdminController::class, 'list'])->name('admin.admin.list');
    Route::get('/admin/admin/add', [AdminController::class, 'add'])->name('admin.admin.add'); 
    Route::post('/admin/admin/add', [AdminController::class, 'insert'])->name('admin.admin.add_A');
    Route::get('/admin/admin/edit/{id}', [AdminController::class, 'edit'])->name('admin.admin.edit');
    Route::post('/admin/admin/edit/{id}', [AdminController::class, 'update'])->name('admin.admin.update');
    Route::get('/admin/admin/delete/{id}', [AdminController::class, 'delete'])->name('admin.admin.delete');

    


    // School Representatives
    Route::get('/admin/school_rep/list', [School_RepController::class, 'exam_list']);
    Route::get('/admin/school_rep/add', [School_RepController::class, 'exam_add']);
    Route::post('/admin/school_rep/add', [School_RepController::class, 'exam_insert']);
    Route::get('/admin/school_rep/edit/{id}', [School_RepController::class, 'exam_edit']); 
    Route::post('/admin/school_rep/edit/{id}', [School_RepController::class, 'exam_update']);
    Route::get('/admin/school_rep/delete/{id}', [School_RepController::class, 'exam_delete']);
    

    // Student routes
    Route::get('/admin/student/list', [StudentController::class, 'list'])->name('admin.student.list');
    Route::get('/admin/student/add', [StudentController::class, 'add'])->name('admin.student.add'); 
    Route::post('/admin/student/add', [StudentController::class, 'insert'])->name('admin.student.add_A');
    Route::get('/admin/student/edit/{id}', [StudentController::class, 'edit'])->name('admin.student.edit'); 
    Route::post('/admin/student/edit/{id}', [StudentController::class, 'update'])->name('admin.student.update');
    Route::get('/admin/student/delete/{id}', [StudentController::class, 'delete'])->name('admin.student.delete');

    //School
    Route::get('/admin/school/list', [SchoolController::class, 'exam_list']);
    Route::get('/admin/school/add', [SchoolController::class, 'exam_add']);
    Route::post('/admin/school/add', [SchoolController::class, 'exam_insert']);
    Route::get('/admin/school/edit/{id}', [SchoolController::class, 'exam_edit']);
    Route::post('/admin/school/edit/{id}', [SchoolController::class, 'exam_update']);
    Route::get('/admin/school/delete/{id}', [SchoolController::class, 'exam_delete']);

    Route::get('/admin/school/exam_schedule', [SchoolController::class, 'exam_schedule']);
    
    
});

Route::group(['middleware' => 'school_rep'], function() {
    Route::get('/school_reps/dashboard', [DashboardController::class, 'dashboard'])->name('school_rep.dashboard'); 
});

Route::group(['middleware' => 'participant'], function() {
    Route::get('/participants/dashboard', [DashboardController::class, 'dashboard'])->name('participant.dashboard'); 
});



Route::get('/home', [App\Http\Controllers\HomeController::class, 'index'])->name('home');

Route::get('/admin/admin/analytics', [App\Http\Controllers\StudentController::class, 'analytics'])->name('admin.admin.analytics');

