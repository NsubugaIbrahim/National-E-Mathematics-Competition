<!-- resources/views/admin/exam/upload_answers.blade.php -->

@extends('layouts.admin')

@section('content')
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h2>Upload Answers</h2>
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
            <form action="{{ route('exam.upload-answers') }}" method="post" enctype="multipart/form-data">
                @csrf
                <div class="form-group">
                    <label for="answers_file">Upload Answers File</label>
                    <input type="file" class="form-control" id="answers_file" name="answers_file" required>
                </div>
                <button type="submit" class="btn btn-primary">Upload</button>
            </form>
        </div>
    </div>
</div>
@endsection
