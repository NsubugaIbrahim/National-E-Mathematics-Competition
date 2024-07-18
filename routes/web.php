<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\ChallengesController;
use App\Http\Controllers\ChallengesviewController;
use App\Models\Challenge;

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
		Route::get('upload', ['as' => 'pages.upload', 'uses' => 'App\Http\Controllers\PageController@icons']);
		Route::get('schools', ['as' => 'pages.schools', 'uses' => 'App\Http\Controllers\PageController@maps']);
		Route::get('notifications', ['as' => 'pages.notifications', 'uses' => 'App\Http\Controllers\PageController@notifications']);
		Route::get('rtl', ['as' => 'pages.rtl', 'uses' => 'App\Http\Controllers\PageController@rtl']);
		Route::get('challenges', ['as' => 'pages.challenges', 'uses' => 'App\Http\Controllers\PageController@tables']);
		Route::get('reps', ['as' => 'pages.reps', 'uses' => 'App\Http\Controllers\PageController@typography']);
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

//Challenges template page
Route::get('/challenges/{challengeId}', function ($challengeId) {
	$challenge = Challenge::where('challengeId', $challengeId)->first();
  
	if (!$challenge) {
	  return abort(404); // Handle non-existent challenge
	}
  
	return view('challenge-details', compact('challenge')); // Render challenge-details.blade.php with data
  })->name('challenge.details');

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


Route::get('/challenges/{challengeId}', function ($challengeId) {
	$challenge = Challenge::where('challengeId', $challengeId)->first();
  
	if (!$challenge) {
	  return abort(404); // Handle non-existent challenge
	}
  
	return view('challenge-details', compact('challenge')); // Render challenge-details.blade.php with data
  })->name('challenge.details');
  
 // Search Route
 Route::get('/admin/challenges/search', 'App\Http\Controllers\ChallengesviewController@searchChallenges')->name('admin.challenges.search');

 // Edit Route
Route::get('/admin/challenges/edit/{challengeId}', 'App\Http\Controllers\ChallengesviewController@editChallenge')->name('admin.challenges.editchallenge');

// Update Route
Route::post('/admin/challenges/update/{challengeId}', 'App\Http\Controllers\ChallengesviewController@updateChallenge')->name('admin.challenges.update');


// Delete Route
Route::delete('/admin/challenges/delete/{challengeId}', 'App\Http\Controllers\ChallengesviewController@destroyChallenge')->name('admin.challenges.destroy');

