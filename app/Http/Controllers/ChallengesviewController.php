<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\challenge;


class ChallengesviewController extends Controller
{
    
    public function viewchallenges()
    {
        $arr['challenges'] = challenge::all(); // Fetch data from your MySQL table
        return view('admin.challenges.viewchallenges')->with($arr);
        dd($arr);
    }

    public function showChallengeDetails($challengeId)
      {
        $challenge = Challenge::where('challengeId', $challengeId)->first();

        if (!$challenge) {
          return abort(404); // Handle non-existent challenge
        }

        $challengeData = $challenge;  // Assign retrieved data to another variable
        return view('challenge-details', compact('challengeData'));
      }

    public function searchChallenges(Request $request)
        {
          $searchTerm = $request->input('search');

          $challenges = Challenge::where('challengeId', 'like', "%{$searchTerm}%")
                                  ->get(); // Adjust search criteria as needed

          return view('admin.challenges.viewchallenges', compact('challenges'));
        }

        public function editChallenge($challengeId)
          {
            $challenge = Challenge::find($challengeId);

            if (!$challenge) {
              return abort(404); // Handle non-existent challenge
            }

            return view('admin.challenges.editchallenge', compact('challenges'));
          }

          public function updateChallenge($challengeId, Request $request)
          {
            $challenge = Challenge::find($challengeId);
          
            if (!$challenge) {
              return abort(404); // Handle non-existent challenge
            }
          
            // Validate and update challenge data using $request object
            // ... (implementation omitted for brevity)
          
            return redirect()->route('admin.challenges.viewchallenges')->with('success', 'Challenge updated successfully!');
          }

          public function destroyChallenge($challengeId)
            {
              $challenge = Challenge::find($challengeId);

              if (!$challenge) {
                return abort(404); // Handle non-existent challenge
              }

              $challenge->delete();

              return redirect()->route('admin.challenges.viewchallenges')->with('success', 'Challenge deleted successfully!');
            }
}
