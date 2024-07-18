<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\school;


class SchoolsviewController extends Controller
{
    public function viewschools() {
        $arr['schools'] = School::all(); // Fetch data
        return view('admin.schools.viewschools')->with($arr);
    }

    //Search function
    public function searchSchools(Request $request)
        {
          $searchTerm = $request->input('search');

          $schools = School::where('schoolId', 'like', "%{$searchTerm}%")
                                  ->get(); // Adjust search criteria as needed

          return view('admin.schools.viewschools', compact('schools'));
        }

        //Edit funtion
        public function editSchool($schoolId)
          {
            $school = School::find($schoolId);

            if (!$school) {
              return abort(404); // Handle non-existent school
            }

            return view('admin.schools.editschool', compact('school'));
          }

          public function updateSchool($schoolId, Request $request)
          {
              $school = School::find($schoolId);
      
              if (!$school) {
                  return abort(404); // Handle non-existent school
              }
      
              $request->validate([
                  'schoolRegNo' => 'required',
                  'schoolName' => 'required',
                  'district' => 'required',
              ]);
      
              $school->update($request->all());
      
              return redirect()->route('schools')->with('success', 'School updated successfully!');
          }
      
          public function destroySchool($schoolId)
              {
                  $school = School::find($schoolId);

                  if (!$school) {
                      return abort(404); // Handle non-existent challenge
                  }

                  $school->delete();
                  return redirect()->route('schools')->with('success', 'School deleted successfully!');
              }
}
