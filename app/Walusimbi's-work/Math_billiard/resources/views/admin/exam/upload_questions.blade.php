<!-- resources/views/admin/exam/upload_questions.blade.php -->

@extends('layouts.admin')

@section('content')
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h2>Upload Questions</h2>
            @if ($errors->any())
                <div class="alert alert-danger">
                    <ul>
                        @foreach ($errors->all() as $error)
                            <li>{{ $error }}</li>
                        @endforeach
                    </ul>
                </div>
            @endif
            @if (session('success'))
                <div class="alert alert-success">
                    {{ session('success') }}
                </div>
            @endif
            <form action="{{ route('exam.upload-questions') }}" method="post" enctype="multipart/form-data">
                @csrf
                <div class="form-group">
                    <label for="questions_file">Upload Questions File</label>
                    <input type="file" class="form-control" id="questions_file" name="questions_file" required>
                </div>
                <button type="submit" class="btn btn-primary">Upload</button>
            </form>
        </div>
    </div>
</div>
@endsection
