@extends('layouts.app', ['pageSlug' => 'participants'])

@section('content')
<html>
<head>
    <style>
         .alert.alert-success{
                    width: 50%;
                }
    </style>
</head>
<body>
    <div class="content">  
        <h4 class="card-title">Participants view</h4> 

        <form action="{{ route('admin.schools.search') }}" method="GET" class="form-inline mb-3">
                <div class="form-group mx-sm-3 mb-2">
                    <label for="search" class="sr-only">Search Participants</label>
                    <input type="text" class="form-control" id="search" name="search" placeholder="Search participants...">
                </div>
                <button type="submit" class="btn btn-primary mb-2">Search</button>
            </form>

                      @if (session('success'))
                            <div class="alert alert-success">{{session('success')}}</div>
                        @endif

        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Participant ID</th>    
                    <th>Username</th>
                    <th>First Name</th>
                    <th>Last Name </th>
                    <th>Email</th>
                    <th>Date of Birth</th>
                    <th>School Reg number</th>
                </tr>
            </thead>
            <tbody>
                @foreach($participants as $c)
                <tr>
                    <td><a href="{{ route('participant.details', $c->participantId) }}">{{$c->participantId}}</a></td>
                    <td><a href="{{ route('participant.details', $c->participantId) }}">{{$c->username}}</a></td>
                    <td>{{$c->firstName}}</td>
                    <td>{{$c->lastName}}</td>
                    <td>{{$c->email}}</td>
                    <td>{{$c->dateOfBirth}}</td>
                    <td>{{$c->schoolRegno}}</td>
                    <td>
                    <a href="{{ route('admin.participants.editparticipant', $c->participantId) }}" class="btn btn-primary btn-sm">Edit</a>
                        <form action="{{ route('admin.participants.destroy', $c->participantId) }}" method="POST" style="display: inline-block">
                            @csrf
                            @method('DELETE')
                            <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this participant?')">Delete</button>
                        </form>
                    </td>
                </tr>
                @endforeach
            </tbody>       
        </table>
    </div>
</body>
</html>
@endsection
