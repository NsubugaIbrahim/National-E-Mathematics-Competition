@extends('layouts.app', ['page' => __('Upload'), 'pageSlug' => 'upload'])

@section('content')
<h1>Upload Questions and Answers</h1>

<div class="container">
  <div class= "row">
  <div class="col-md-8 mt-5">
    <div class ="card">
      <div class="card-header">
        <h4>Import Questions excel document</h4>
      </div>
      <div class="card-body">
      <form method="POST" action="{{ route('questions.import') }}" enctype="multipart/form-data">
        @csrf
          <input type="file" name="import_file" class="form-control"/>
          <button class="btn btn-primary">Import</button>
      </form>
    </div>
    </div>
  </div>
  </div>
</div>

      @if (session('status'))
        <div class="alert alert-success">{{session('status')}}</div>
      @endif

<div class="container">
  <div class= "row">
  <div class="col-md-8 mt-5">
    <div class ="card">
      <div class="card-header">
        <h4>Import Answers excel document</h4>
      </div>
      <div class="card-body">
      <form method="POST" action="{{ route('answers.import') }}" enctype="multipart/form-data">
        @csrf
          <input type="file" name="import_answers_file" class="form-control"/>
          <button class="btn btn-primary">Import</button>
        </form>
    </div>
    </div>
  </div>
  </div>
</div>
@endsection