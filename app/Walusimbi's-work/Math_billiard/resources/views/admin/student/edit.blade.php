<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>AdminLTE 3 | Edit Student</title>

  <!-- Google Font: Source Sans Pro -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/admin-lte/3.1.0/css/adminlte.min.css">

  <style>
  .btn-circle {
    border-radius: 50%;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    width: 40px;
    height: 40px;
    padding: 0;
    text-align: center;
    font-size: 16px;
  }

  .btn-icon {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    padding: 10px 20px;
    font-size: 16px;
  }

  .btn-light {
    background-color: #f8f9fa;
    border-color: #f8f9fa;
    color: #000;
  }

  .btn-warning {
    background-color: #ffc107;
    border-color: #ffc107;
    color: #fff;
  }

  .btn-primary {
    background-color: #007bff;
    border-color: #007bff;
    color: #fff;
  }
  </style>
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
            <h1>Edit Student</h1>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <div class="row">
          <!-- left column -->
          <div class="col-md-12">
            <!-- general form elements -->
            <div class="card card-primary">
              
              <!-- /.card-header -->
              <!-- form start -->
              <form method="post" action="" enctype="multipart/form-data">
              {{ csrf_field()}}
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
                    <div class="form-group col-md-6">
                        <label>First Name <span style="color: red;">*</span></label>
                        <input type="text" class="form-control" value="{{ old('name', $getRecord->name ) }}" name="name" required placeholder="First Name">
                        <div style="color:red">{{ $errors->first('name')}}</div>
                    </div>

                    <div class="form-group col-md-6">
                        <label>Last Name <span style="color: red;">*</span></label>
                        <input type="text" class="form-control" value="{{ old('last_name' , $getRecord->last_name)}}" name="last_name" required placeholder="Last Name">
                        <div style="color:red">{{ $errors->first('last_name')}}</div>
                    </div>

                    <div class="form-group col-md-6">
                        <label>Admission Number <span style="color: red;">*</span></label>
                        <input type="text" class="form-control" value="{{ old('admission_number' , $getRecord->admission_number)}}" name="admission_number" required placeholder="Admission Number">
                        <div style="color:red">{{ $errors->first('admission_number')}}</div>
                    </div>

                    <div class="form-group col-md-6">
                        <label>Class <span style="color: red;">*</span></label>
                        <select class="form-control" name="class_id">
                            <option value="">Select class</option>
                            <option {{ (old('class_id' , $getRecord->class_id) == 1) ? 'selected' : '' }} value="1">P.7 finalist</option>
                            <option {{ (old('class_id', $getRecord->class_id) == 2) ? 'selected' : '' }} value="2">P.4 - P.6 continuing</option>
                        </select>
                        <div style="color:red">{{ $errors->first('class_id')}}</div>
                    </div>

                    <div class="form-group col-md-6">
                        <label>Gender <span style="color: red;">*</span></label>
                        <select class="form-control" required name="gender">
                            <option value="">Select Gender</option>
                            <option {{ (old('gender') == 'Male' || $getRecord->gender == 'Male') ? 'selected' : '' }} value="Male">Male</option>
                            <option {{ (old('gender') == 'Female' || $getRecord->gender == 'Female') ? 'selected' : '' }} value="Female">Female</option>
                        </select>

                        <div style="color:red">{{ $errors->first('gender')}}</div>
                    </div>

                    <div class="form-group col-md-6">
                        <label>Date Of Birth <span style="color: red;">*</span></label>
                        <input type="date" class="form-control" value="{{ old('date_of_birth', $getRecord->date_of_birth)}}" name="date_of_birth" required>
                        <div style="color:red">{{ $errors->first('date_of_birth')}}</div>
                    </div>

                    <div class="form-group col-md-6">
                        <label>Religion</label>
                        <input type="text" class="form-control" value="{{ old('religion', $getRecord->religion)}}" name="religion" placeholder="Religion">
                    </div>

                    <div class="form-group col-md-6">
                        <label>Admission Date <span style="color: red;">*</span></label>
                        <input type="date" class="form-control" value="{{ old('admission_date', $getRecord->admission_date)}}" name="admission_date" required>
                        <div style="color:red">{{ $errors->first('admission_date')}}</div>
                    </div>

                    <div class="form-group col-md-6">
                        <label for="profile_pic">Profile Pic</label>
                        <input type="file" class="form-control" id="profile_pic" name="profile_pic" onchange="previewProfilePic(event)">
                        <div style="color:red">{{ $errors->first('profile_pic')}}</div>
                        @if(!empty($getRecord->getProfile()))
                            <img id="preview_profile_pic" src="{{ $getRecord->getProfile() }}" style="width: 70px;">
                        @else
                            <img id="preview_profile_pic" src="{{ asset('path_to_default_image_or_placeholder') }}" style="width: 100px;">
                        @endif
                    </div>


                    <div class="form-group col-md-6">
                        <label>Status <span style="color: red;">*</span></label>
                        <select class="form-control" required name="status">
                            <option value="">Select Status</option>
                            <option {{ (old('status', $getRecord->status) == 1) ? 'selected' : '' }} value="1">Active</option>
                            <option {{ (old('status', $getRecord->status) == 2) ? 'selected' : '' }} value="2">Inactive</option>
                        </select>
                        <div style="color:red">{{ $errors->first('status')}}</div>
                    </div>
                </div>
                </div>
                  
                <div class="form-group" style="max-width: 300px; margin-left: 20px;">
                    <label>Email <span style="color: red;">*</span></label>
                    <input type="email" class="form-control" value="{{ old('email', $getRecord->email)}}" name="email" required placeholder="Email">
                    <div style="color:red">{{ $errors->first('email')}}</div>
                </div>
                <div class="form-group" style="max-width: 300px; margin-left: 20px;">
                    <label>Password <span style="color: red;">*</span></label>
                    <input type="text" class="form-control" name="password"  placeholder="Password">
                    <div style="color:red">{{ $errors->first('password')}}</div>
                    <p>If you want to change Password, please enter a new password</p>
                </div>

                <!-- /.card-body -->
                <div class="card-footer d-flex justify-content-between">
                <a href="javascript:history.back()" class="btn btn-light btn-circle">
                    <i class="fas fa-arrow-left"></i>
                </a>
                <div class="text-center" style="flex-grow: 1;">
                    <button type="button" class="btn btn-warning btn-icon ml-3" onclick="document.querySelector('form').reset();">
                    <i class="fas fa-eraser"> Clear</i>
                    </button>
                </div>
                <button type="submit" class="btn btn-primary btn-icon ml-auto">
                    <i class="fas fa-paper-plane"> Update</i>
                </button>
                </div>

              </form>
            </div>
            <!-- /.card -->
          </div>
          <!--/.col (right) -->
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

<!-- ./wrapper -->

<!-- jQuery -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/admin-lte/3.1.0/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/admin-lte/3.1.0/js/demo.js"></script>

<script>
    function previewProfilePic(event) {
        var input = event.target;
        var reader = new FileReader();
        reader.onload = function(){
            var preview = document.getElementById('preview_profile_pic');
            preview.src = reader.result;
        };
        reader.readAsDataURL(input.files[0]);
    }
</script>

</body>
</html>
