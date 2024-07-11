<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\ChallengesController;
use App\Http\Controllers\ChallengesviewController;
/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "web" middleware group. Make something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});

Auth::routes();

Route::get('/home', [App\Http\Controllers\HomeController::class, 'index'])->name('home');
Auth::routes();

Route::get('/home', 'App\Http\Controllers\HomeController@index')->name('home')->middleware('auth');

Route::group(['middleware' => 'auth'], function () {
		Route::get('icons', ['as' => 'pages.icons', 'uses' => 'App\Http\Controllers\PageController@icons']);
		Route::get('maps', ['as' => 'pages.maps', 'uses' => 'App\Http\Controllers\PageController@maps']);
		Route::get('notifications', ['as' => 'pages.notifications', 'uses' => 'App\Http\Controllers\PageController@notifications']);
		Route::get('rtl', ['as' => 'pages.rtl', 'uses' => 'App\Http\Controllers\PageController@rtl']);
		Route::get('tables', ['as' => 'pages.tables', 'uses' => 'App\Http\Controllers\PageController@tables']);
		Route::get('typography', ['as' => 'pages.typography', 'uses' => 'App\Http\Controllers\PageController@typography']);
		Route::get('upgrade', ['as' => 'pages.upgrade', 'uses' => 'App\Http\Controllers\PageController@upgrade']);
});

Route::group(['middleware' => 'auth'], function () {
	Route::resource('user', 'App\Http\Controllers\UserController', ['except' => ['show']]);
	Route::get('profile', ['as' => 'profile.edit', 'uses' => 'App\Http\Controllers\ProfileController@edit']);
	Route::put('profile', ['as' => 'profile.update', 'uses' => 'App\Http\Controllers\ProfileController@update']);
	Route::put('profile/password', ['as' => 'profile.password', 'uses' => 'App\Http\Controllers\ProfileController@password']);
});

//Route for submitting Challenge table's details
Route::post('/submit-challenge', [App\Http\Controllers\ChallengesController::class, 'store'])->name('submit');

//Route for success page
Route::get('/success_page', function () {
    return view('success_page');
})->name('success_page');

//Route for viewing challenges
Route::get('/admin/challenges', [App\Http\Controllers\ChallengesviewController::class, 'viewchallenges'])->name('challenges');

//Route for submitting Schools table's details
Route::post('/submit-schools', [App\Http\Controllers\SchoolsController::class, 'store'])->name('submitschools');

//Route for school success page
Route::get('/success_schools_submit', function () {
    return view('success_schools_submit');
})->name('success_schools_submit');

//Route for viewing schools
Route::get('/admin/schools', [App\Http\Controllers\SchoolsviewController::class, 'viewschools'])->name('schools');

//Route for submitting Representatives table's details
Route::post('/submit-representatives', [App\Http\Controllers\RepresentativeController::class, 'store'])->name('submit_reps');

//Route for representatives success page
Route::get('/success_reps_submit', function () {
    return view('success_reps_submit');
})->name('success_reps_submit');

//Route for viewing representatives
Route::get('/admin/representatives', [App\Http\Controllers\RepresentativesviewController::class, 'viewrepresentatives'])->name('representatives');

//Questions Excel documents import
Route::post('questions/import', [App\Http\Controllers\QuestionController::class, 'importExcelQuestion'])->name('questions.import');

//Answers Excel documents import
Route::post('answers/import', [App\Http\Controllers\AnswerController::class, 'importExcelAnswer'])->name('answers.import');