<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Participant;

class ParticipantController extends Controller
{
    public function viewparticipants() {
        $arr['participants'] = Participant::all(); // Fetch data
        return view('pages.notifications')->with($arr);
    }

    //Search function
    public function searchParticipants(Request $request)
        {
          $searchTerm = $request->input('search');

          $participants = Participant::where('participantId', 'like', "%{$searchTerm}%")
                                  ->get(); // Adjust search criteria as needed

          return view('pages.notifications', compact('participants'));
        }

        //Edit funtion
        public function editParticipant($participantId)
          {
            $participant = Participant::find($participantId);

            if (!$participant) {
              return abort(404); // Handle non-existent participant
            }

            return view('admin.participants.editparticipant', compact('participant'));
          }

          public function updateParticipant($participantId, Request $request)
          {
              $participant = Participant::find($participantId);
      
              if (!$participant) {
                  return abort(404); // Handle non-existent participant
              }
      
              $request->validate([
                  'username' => 'required',
                  'firstName' => 'required',
                  'lastName' => 'required',
                  'email' => 'required',
                  'dateOfBirth' => 'required',
                  'schoolRegno' => 'required',
              ]);
      
              $participant->update($request->all());
      
              return redirect()->route('participants')->with('success', 'Participant updated successfully!');
          }
      
          public function destroyParticipant($participantId)
              {
                  $participant = Participant::find($participantId);

                  if (!$participant) {
                      return abort(404); // Handle non-existent participant
                  }

                  $participant->delete();
                  return redirect()->route('participants')->with('success', 'Participant deleted successfully!');
              }
}
