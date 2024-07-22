@extends('layouts.app', ['pageSlug' => 'analytics'])

@section('content')
<head>
    <style>
            #serial th,
            #serial td {
                    text-align: center;
                    }
            .container h4 {
            color: pink; 
        }
        .container h4:hover {
            color: blue; 
        }
    </style>
</head>
    <div class="container">
        <h1 class="card-title"><i class="tim-icons icon-chart-bar-32"></i> Analytics</h1>
        <div class="card ">
            <div class="table-responsive">
                <table class="table tablesorter " id="">
                    <ul>
                        <tr><th><a href="#Correct"><h4><li>Most Correctly Answered Questions</li></h4></a></th></tr>
                        <tr><th><a href="#Ranks"><h4><li>School Rankings</li></h4></a></th></tr>
                        <tr><th><a href="#Performance"><h4><li>Performance of schools and participants over the years</li></h4></a></th></tr>
                        <tr><th><a href="#Repetition"><h4><li>Percentage Repetition of Questions</li></h4></a></th></tr>
                        <tr><th><a href="#Worst"><h4><li>Worst Performing Schools for a Challenge</li></h4></a></th></tr>
                        <tr><th> <a href="#Best"><h4><li>Best Performing Schools for All Challenges</li></h4></a></th></tr>
                        <tr><th><a href="#Incomplete"><h4><li>Participants with Incomplete Challenges</li></h4></a></th></tr>
                    <ul>
                </table>
            </div>
        </div>

        <div id="Correct">
        <h3>Most Correctly Answered Questions</h3>
            <div class="card ">
                <div class="table-responsive">
                    <table class="table tablesorter" id="serial">
                        <thead>
                            <tr> 
                                <th><h5>#</h5></th>
                                <th><h5>Question ID</h5></th>
                                <th><h5>Number of times answered correct</h5></th>
                            </tr>
                        </thead>                   
                            <tbody>                        
                            @foreach($mostCorrectlyAnsweredQuestions as $question)
                                <tr>                         
                                    <td>{{ $question->questionId }}</td>
                                    <td>{{ $question->correct_answers }}</td>
                                </tr>
                            @endforeach                          
                            </tbody>                    
                    </table>
                </div>
            </div>
        </div> 

        <div id="Ranks">
            <h3>School Rankings</h3>
            <div class="card ">
                <div class="table-responsive">
                    <table border ='1'class="table tablesorter" id="serial">
                        
                        <thead>
                            <tr> 
                                <th><h5>#</h5></th>
                                <th><h5>School Reg No</h5></th>
                                <th><h5>Total Correct Answers</h5></th>
                            </tr>
                        </thead>                   
                        <tbody> 
                            @foreach($schoolRankings as $school)
                            <tr> 
                                <td>{{ $school->schoolRegno }}</td> 
                                <td>{{ $school->total_correct }}</td>                   
                            </tr>           
                            @endforeach
                        </tbody> 
                        </table>
                </div> 
            </div>
        </div>  

        <div id="Performance">
            <h3>Performance of schools and participants over the years</h3>
            <div class="card ">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped" id="serial">
                    <style>
                        .blue-background {
                            background-color: brown;
                            color: white; /* Optional: to make the text readable */
                        }
                    </style>
                        <thead>
                            <tr>
                                <th><h5>#</h5></th>
                                <th><h5>Year</h5></th>
                                <th><h5>Username</h5></th>
                                <th><h5>School Reg No</h5></th>                            
                                <th><h5>Total Correct Answers</h5></th>
                            </tr>
                        </thead>
                        <tbody>
                            @foreach($performanceOverTime as $performance)
                            <tr>
                                <td>{{ $performance->year }}</td>
                                <td>{{ $performance->username }}</td>
                                <td>{{ $performance->schoolRegno }}</td>
                                <td>{{ $performance->total_correct }}</td>
                            </tr>
                            @endforeach
                        </tbody>
                    </table>
                </div> 
            </div>
        </div> 

        <div id="Repetition">
            <h2>Percentage Repetition of Questions</h2>
                <div class="card ">
                    <div class="table-responsive">
                        <table class="table table-bordered table-striped" id="serial">
                        <style>
                            .blue-background {
                                background-color: brown;
                                color: white; /* Optional: to make the text readable */
                            }
                        </style>
                            <thead>
                                <tr>
                                    <th><h5>#</h5></th>
                                    <th><h5>Question ID</h5></th>
                                    <th><h5>Number of repeated attempts</h5></th>                     
                                    <th><h5>Repetition Percentage</h5></th>
                                </tr>
                            </thead>
                            <tbody>
                                @foreach($repetition as $rep)
                                <tr>
                                    <td>{{ $rep->questionId }}</td>
                                    <td>{{ $rep->total_attempts }}</td>                             
                                    <td>{{ $rep->repetition_percentage }}%</td>
                                </tr>
                                @endforeach
                            </tbody>
                        </table>
                    </div> 
                </div>
        </div>  


            <div id="Worst">
                <h2>Worst Performing Schools for a Challenge</h2>            
                <div class="card ">
                    <div class="table-responsive">
                        <table class="table table-bordered table-striped" id="serial">
                        <style>
                            .blue-background {
                                background-color: brown;
                                color: white; /* Optional: to make the text readable */
                            }
                        </style>
                            <thead>
                                <tr>
                                    <th><h5>#</h5></th>
                                    <th><h5>School Reg No</h5></th>
                                    <th><h5>Total Correct Answers</h5></th>
                                </tr>
                            </thead>
                            <tbody>
                                @foreach($worstPerformingSchools as $school)
                                <tr>
                                    <td>{{ $school->schoolRegno }}</td>
                                    <td>{{ $school->total_correct }}</td>
                                </tr>
                                @endforeach
                            </tbody>
                        </table>
                    </div> 
                </div>
            </div>  
            
            <div id="Best">
                <h3>Best Performing Schools for All Challenges</h3>
                <div class="card ">
                    <div class="table-responsive">
                        <table class="table table-bordered table-striped" id="serial">
                        <style>
                            .blue-background {
                                background-color: brown;
                                color: white; /* Optional: to make the text readable */
                            }
                        </style>
                            <thead>
                                <tr>
                                    <th><h5>#</h5></th>
                                    <th><h5>School Reg No</h5></th>
                                    <th><h5>Total Correct Answers</h5></th>
                                </tr>
                            </thead>
                            <tbody>
                                @foreach($bestPerformingSchools as $school)
                                <tr>
                                    <td>{{ $school->schoolRegno }}</td>
                                    <td>{{ $school->total_correct }}</td>                               
                                </tr>
                                @endforeach
                            </tbody>
                        </table>
                    </div> 
                </div>
            </div>  
            
            <div id="Incomplete">
                <h3>Participants with Incomplete Challenges</h3>
                <div class="card ">
                    <div class="table-responsive">
                        <table class="table table-bordered table-striped" id="serial">
<style>
    .blue-background {
        background-color: brown;
        color: white; /* Optional: to make the text readable */
    }
</style>

<thead>
    <tr class="blue-background">
        <th><h5>#</h5></th>
        <th><h5>Participant ID</h5></th>
        <th><h5>Username</h5></th>
        <th><h5>Number of Incomplete Challenges</h5></th>
    </tr>
</thead>

                            <tbody>
                                @foreach($incompleteChallenges as $participant)
                                <tr>
                                    <td>{{ $participant->participantId }}</td>
                                    <td>{{ $participant->username }}</td>
                                    <td>{{ $participant->incomplete_challenges }}</td>
                                </tr>
                                @endforeach
                            </tbody>
                        </table>
                    </div> 
                </div>
            </div>  





           
</div>
@endsection

<script>
       function addSerialNumber() {
  // Get all tables with the ID "serial"
  const tables = document.querySelectorAll('table[id="serial"]');

  // Loop through each table
  for (const table of tables) {
    const rows = table.getElementsByTagName('tr');

    // Start numbering from 1 for each table
    let count = 1;

    for (let i = 1; i < rows.length; i++) {
      const serialCell = rows[i].insertCell(0);
      serialCell.textContent = count;
      count++; // Increment counter for the next row in this table
    }
  }
}

// Call the function when the page loads
window.addEventListener('load', addSerialNumber);

</script>