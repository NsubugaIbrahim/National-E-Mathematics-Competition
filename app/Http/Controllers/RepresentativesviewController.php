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

    //Search function
    public function searchRepresentatives(Request $request)
        {
          $searchTerm = $request->input('search');

          $representatives = Representative::where('representativeId', 'like', "%{$searchTerm}%")
                                  ->orWhere('schoolRegNo', 'like', "%{$searchTerm}%")
                                  ->orWhere('representativeName', 'like', "%{$searchTerm}%")
                                  ->get(); // Adjust search criteria as needed

          return view('admin.representatives.viewrepresentatives', compact('representatives'));
        }

        //Edit funtion
        public function editRepresentative($representativeId)
          {
            $representative = Representative::find($representativeId);

            if (!$representative) {
              return abort(404); // Handle non-existent representative
            }

            return view('admin.representatives.editrepresentative', compact('representative'));
          }

          public function updateRepresentative($representativeId, Request $request)
          {
              $representative = Representative::find($representativeId);
      
              if (!$representative) {
                  return abort(404); // Handle non-existent representative
              }
      
              $request->validate([
                  'representativeName' => 'required',
                  'representativeEmail' => 'required',
                  'schoolRegNo' => 'required',
              ]);
      
              $representative->update($request->all());
      
              return redirect()->route('representatives')->with('success', 'Representative updated successfully!');
          }
      
          public function destroyRepresentative($representativeId)
              {
                  $representative = Representative::find($representativeId);

                  if (!$representative) {
                      return abort(404); // Handle non-existent representative
                  }

                  $representative->delete();
                  return redirect()->route('representatives')->with('success', 'Representative deleted successfully!');
              }
}
