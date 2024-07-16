<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Admin\AuthController;
use App\Http\Controllers\DashboardController;
use App\Http\Controllers\AdminController;
use App\Http\Controllers\AnalyticsController;
use App\Http\Controllers\StudentController;
use App\Http\Controllers\ExaminationsController;
use App\Http\Controllers\SchooLController;
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

    // School 
    Route::get('/admin/schools/list', [SchoolController::class, 'list']);
    Route::get('/admin/schools/add', [SchoolController::class, 'add']);
    
    Route::get('/admin/schools/edit/{id}', [SchoolController::class, 'edit']); 
    Route::post('/admin/schools/edit/{id}', [SchoolController::class, 'update']);
    Route::get('/admin/schools/delete/{id}', [SchoolController::class, 'delete']);
    Route::post('/admin/schools/add', [SchoolController::class, 'insert'])->name('insert.schools');



    // School Representatives
    Route::get('/admin/school_rep/list', [School_RepController::class, 'list']);
    Route::get('/admin/school_rep/add', [School_RepController::class, 'add']);
    Route::post('/admin/school_rep/add', [School_RepController::class, 'insert']);
    Route::get('/admin/school_rep/edit/{id}', [School_RepController::class, 'edit']); 
    Route::post('/admin/school_rep/edit/{id}', [School_RepController::class, 'update']);
    Route::get('/admin/school_rep/delete/{id}', [School_RepController::class, 'delete']);
    Route::get('/admin/school_rep/my_student/{id}', [School_RepController::class, 'myStudent']);

    // Student routes
    Route::get('/admin/student/list', [StudentController::class, 'list'])->name('admin.student.list');
    Route::get('/admin/student/add', [StudentController::class, 'add'])->name('admin.student.add'); 
    Route::post('/admin/student/add', [StudentController::class, 'insert'])->name('admin.student.add_A');
    Route::get('/admin/student/edit/{id}', [StudentController::class, 'edit'])->name('admin.student.edit'); 
    Route::post('/admin/student/edit/{id}', [StudentController::class, 'update'])->name('admin.student.update');
    Route::get('/admin/student/delete/{id}', [StudentController::class, 'delete'])->name('admin.student.delete');

    //Examinations
    Route::get('/admin/exam/list', [ExaminationsController::class, 'exam_list']);
    Route::get('/admin/exam/add', [ExaminationsController::class, 'exam_add']);
    Route::post('/admin/exam/add', [ExaminationsController::class, 'exam_insert']);
    Route::get('/admin/exam/edit/{id}', [ExaminationsController::class, 'exam_edit']);
    Route::post('/admin/exam/edit/{id}', [ExaminationsController::class, 'exam_update']);
    Route::get('/admin/exam/delete/{id}', [ExaminationsController::class, 'exam_delete']);

    Route::get('/admin/exam/exam_schedule', [ExaminationsController::class, 'exam_schedule']);
    
    // Question and Answer uploads
    Route::get('admin/exam/upload-questions', [ExaminationsController::class, 'uploadQuestionsForm'])->name('exam.upload-questions');
    Route::post('admin/exam/upload-questions', [ExaminationsController::class, 'uploadQuestions'])->name('exam.upload-questions');

    Route::get('admin/exam/upload-answers', [ExaminationsController::class, 'uploadAnswersForm'])->name('exam.upload-answers');
    Route::post('admin/exam/upload-answers', [ExaminationsController::class, 'uploadAnswers'])->name('exam.upload-answers');
    Route::post('admin/exam/insert', [ExaminationsController::class, 'exam_insert'])->name('exam.insert');

});

Route::group(['middleware' => 'school_rep'], function() {
    Route::get('/school_reps/dashboard', [DashboardController::class, 'dashboard'])->name('school_rep.dashboard'); 
});

Route::group(['middleware' => 'participant'], function() {
    Route::get('/participants/dashboard', [DashboardController::class, 'dashboard'])->name('participant.dashboard'); 
});



Route::get('/home', [App\Http\Controllers\HomeController::class, 'index'])->name('home');

Route::get('/admin/admin/analytics', [App\Http\Controllers\StudentController::class, 'analytics'])->name('admin.admin.analytics');

