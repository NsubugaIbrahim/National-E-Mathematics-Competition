@extends('layouts.app', ['pageSlug' => 'analytics'])

@section('content')
    <div class="container">
        <h1 class="card-title">Participants</h1> 
        <h3>Most Correctly Answered Questions</h3>
        <div class="card ">
            <div class="table-responsive">
                <table class="table tablesorter " id="">
                    <thead>
                        <tr> 
                            <th>Question ID</th>
                            <th>Number of times answered correct</th>
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
        
        <h2>School Rankings</h2>
        <ul>
            @foreach($schoolRankings as $school)
                <li>School ID: {{ $school->school_id }}, Average Score: {{ $school->average_score }}</li>
            @endforeach
        </ul>
    </div>
@endsection
