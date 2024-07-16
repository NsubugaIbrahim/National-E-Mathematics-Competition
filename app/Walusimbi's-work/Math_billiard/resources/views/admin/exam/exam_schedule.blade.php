<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>AdminLTE 3 | Simple Tables</title>

  <!-- Google Font: Source Sans Pro -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="../../plugins/fontawesome-free/css/all.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="../../dist/css/adminlte.min.css">
</head>
<body class="hold-transition sidebar-mini">
@include('layouts.header')

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">

      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>Exam Schedule </h1>
          </div>
          

        </div>
      </div><!-- /.container-fluid -->
    </section>

    <div class="row">
          <!-- left column -->



          <div class="col-md-12">
            <!-- general form elements -->
            <div class="card card-primary mx-2">
            <div class="card-header ">
                <h3 class="card-title">Search Exam Schedule</h3>
              </div>
              <form method="get" action="">
               <!-- Display Validation Errors -->
               @if ($errors->any())
                    <div class="alert alert-danger">
                        <ul>
                            @foreach ($errors->all() as $error)
                                <li>{{ $error }}</li>
                            @endforeach
                        </ul>
                    </div>
                @endif
                <div class="card-body">

                  <div class="row">

                  
                  <div class="form-group col-md-3">
  <label>Exam</label>
  <select class="form-control" name="exam_id" required>
    <option value="">Select</option>
    @foreach ($getExam as $exam)
      <option value="{{ $exam->id }}" {{ (Request::get('exam_id') == $exam->id) ? 'selected' : '' }}>
        {{ $exam->name }}
      </option>
    @endforeach
  </select>
</div>


                  <div class="form-group col-md-3">
                    <label>Class</label>
                    <select class="form-control" name="class_id" >
                        <option value="">Select</option>
                        
                    </select>
                  </div>
                  

                  <div class="form-group col-md-3 align-self-end">
                        <button class="btn btn-success" type="submit">Search</button>
                        <a href={{ url('admin/exam/exam_schedule')}} class="btn btn-primary">Clear</a>
                    </div>
                       
                  </div>
                  
                <!-- /.card-body -->

                
              </form>
            </div>
            <!-- /.card -->

            
          <!--/.col (right) -->
        </div>

    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <div class="row">
          <div class="col-md-12">
            
            <div class="card">
              <div class="card-header">
                <h3 class="card-title">Exam Schedule</h3>
              </div>
              <!-- /.card-header -->
              <div class="card-body p-0">
                <table class="table table-striped">
                  <thead>
                    <tr>
                      <th>Topic Name</th>
                      <th>Date</th>
                      <th>Start Time</th>
                      <th>End Time</th>
                      <th>Full Marks</th>
                      <th>Passing Marks</th>
                    </tr>
                  </thead>
                 
                </table>

                

              </div>
              <!-- /.card-body -->
            </div>
            <!-- /.card -->
          </div>
          <!-- /.col -->
        </div>
        <!-- /.row -->
        
      </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  @include('layouts.footer')
  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Control sidebar content goes here -->
  </aside>
  <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->

<!-- jQuery -->
<script src="../../plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="../../plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="../../dist/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="../../dist/js/demo.js"></script>
</body>
</html>

