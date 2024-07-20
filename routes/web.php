<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\ChallengesController;
use App\Http\Controllers\ChallengesviewController;
use App\Models\Challenge;
use App\Models\School;
use App\Models\Representative;
use App\Models\Participant;

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
		Route::get('participants', ['as' => 'pages.participants', 'uses' => 'App\Http\Controllers\PageController@notifications']);
		Route::get('analytics', ['as' => 'pages.analytics', 'uses' => 'App\Http\Controllers\PageController@rtl']);
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

 // Search Route
 Route::get('/admin/challenges/search', 'App\Http\Controllers\ChallengesviewController@searchChallenges')->name('admin.challenges.search');

 // Edit Route
Route::get('/admin/challenges/edit/{challengeId}', 'App\Http\Controllers\ChallengesviewController@editChallenge')->name('admin.challenges.editchallenge');

// Update Route
Route::post('/admin/challenges/update/{challengeId}', 'App\Http\Controllers\ChallengesviewController@updateChallenge')->name('admin.challenges.update');


// Delete Route
Route::delete('/admin/challenges/delete/{challengeId}', 'App\Http\Controllers\ChallengesviewController@destroyChallenge')->name('admin.challenges.destroy');




//Route for submitting Schools table's details
Route::post('/submit-schools', [App\Http\Controllers\SchoolsController::class, 'store'])->name('submitschools');

//Route for school success page
Route::get('/success_schools_submit', function () {
    return view('success_schools_submit');
})->name('success_schools_submit');

//Route for viewing schools
Route::get('/admin/schools', [App\Http\Controllers\SchoolsviewController::class, 'viewschools'])->name('schools');

//Schools template page
Route::get('/school/{schoolId}', function ($schoolId) {
	$school = School::where('schoolId', $schoolId)->first();
  
	if (!$school) {
	  return abort(404); // Handle non-existent school
	}
  
	return view('school-details', compact('school')); // Render school-details.blade.php with data
  })->name('school.details');

// Search Route
Route::get('/admin/schools/search', 'App\Http\Controllers\SchoolsviewController@searchSchools')->name('admin.schools.search');

// Edit Route
Route::get('/admin/schools/edit/{schoolId}', 'App\Http\Controllers\SchoolsviewController@editSchool')->name('admin.schools.editschool');

// Update Route
Route::post('/admin/schools/update/{schoolId}', 'App\Http\Controllers\SchoolsviewController@updateSchool')->name('admin.schools.update');


// Delete Route
Route::delete('/admin/schools/delete/{schoolId}', 'App\Http\Controllers\SchoolsviewController@destroySchool')->name('admin.schools.destroy');





//Route for submitting Representatives table's details
Route::post('/submit-representatives', [App\Http\Controllers\RepresentativeController::class, 'store'])->name('submit_reps');

//Route for representatives success page
Route::get('/success_reps_submit', function () {
    return view('success_reps_submit');
})->name('success_reps_submit');

//Route for viewing representatives
Route::get('/admin/representatives', [App\Http\Controllers\RepresentativesviewController::class, 'viewrepresentatives'])->name('representatives');

//Representatives template page
Route::get('/representative/{representativeId}', function ($representativeId) {
	$representative = Representative::where('representativeId', $representativeId)->first();
  
	if (!$representative) {
	  return abort(404); // Handle non-existent representative
	}
  
	return view('representative-details', compact('representative')); // Render representative-details.blade.php with data
  })->name('representative.details');


// Search Route
Route::get('/admin/representatives/search', 'App\Http\Controllers\RepresentativesviewController@searchRepresentatives')->name('admin.representatives.search');

// Edit Route
Route::get('/admin/representatives/edit/{representativeId}', 'App\Http\Controllers\RepresentativesviewController@editRepresentative')->name('admin.representatives.editrepresentative');

// Update Route
Route::post('/admin/representatives/update/{representativeId}', 'App\Http\Controllers\RepresentativesviewController@updateRepresentative')->name('admin.representatives.update');


// Delete Route
Route::delete('/admin/representatives/delete/{representativeId}', 'App\Http\Controllers\RepresentativesviewController@destroyRepresentative')->name('admin.representatives.destroy');


//Route for viewing participants
Route::get('participants', [App\Http\Controllers\ParticipantController::class, 'viewparticipants'])->name('participants');

//Participants template page
Route::get('/participant/{participantId}', function ($participantId) {
	$participant = Participant::where('participantId', $participantId)->first();
  
	if (!$participant) {
	  return abort(404); // Handle non-existent participant
	}
  
	return view('participant-details', compact('participant')); // Render participant-details.blade.php with data
  })->name('participant.details');


  // Search Route
  Route::get('/admin/participants/search', 'App\Http\Controllers\ParticipantController@searchParticipants')->name('admin.participants.search');
  
  // Edit Route
  Route::get('/admin/participants/edit/{participantId}', 'App\Http\Controllers\ParticipantController@editParticipant')->name('admin.participants.editparticipant');
  
  // Update Route
  Route::post('/admin/participants/update/{participantId}', 'App\Http\Controllers\ParticipantController@updateParticipant')->name('admin.participants.update');
  
  
  // Delete Route
  Route::delete('/admin/participants/delete/{participantId}', 'App\Http\Controllers\ParticipantController@destroyParticipant')->name('admin.participants.destroy');




//Questions Excel documents import
Route::post('questions/import', [App\Http\Controllers\QuestionController::class, 'importExcelQuestion'])->name('questions.import');

//Answers Excel documents import
Route::post('answers/import', [App\Http\Controllers\AnswerController::class, 'importExcelAnswer'])->name('answers.import');

//API End points
Route::post('/api/attempts', [App\Http\Controllers\AttemptController::class, 'store']);

//Analytics
Route::get('analytics', [App\Http\Controllers\AnalyticsController::class, 'analytics'])->name('analytics');


//Schools template page
Route::get('/school/{schoolRegno}', function ($schoolRegno) {
	$school = School::where('schoolId', $schoolId)->first();
  
	if (!$school) {
	  return abort(404); // Handle non-existent school
	}
  
	return view('school-details', compact('school')); // Render school-details.blade.php with data
  })->name('school.details');