@extends('layouts.app', ['page' => __('Challenges'), 'pageSlug' => 'challenges'])

@section('content')
<html>
<head>
    <title>School Registration</title>
    <style>
        .availability {
            font-size: 20px;
            text-align: center; /* Center horizontally */
            vertical-align: middle; /* Center vertically */
        }
        .right{
            float: right;
        }
        .card-title, .right {
            display: inline;
            
        }
    </style>
</head>
<body>
    <div class="content">  
        <h1 class="card-title">Challenges <h4>Insert new Challenges into the table and upload to update the Database</h4> </h1> 
        
        <div class="right">
        <div class="alert alert-primary">
          <button type="button" aria-hidden="true" class="close" data-dismiss="alert" aria-label="Close">
            <i class="tim-icons icon-simple-remove"></i>
          </button>
          <span>
            <b> Info - </b> Insert both dates to define the Challenge Availability</span>
        </div>
    </div>
    <button id="addRow"><i class="tim-icons icon-simple-add"></i>        Add New Challenge</button>
    <!-- <p class="card-category">Add School Representatives to validate</p> -->
    
    <form method="post" action="/submit-challenge">
    @csrf
    <table border="1">
        <thead>
            <tr>
                <th>Number of questions</th>
                <th>Duration</th>
                <th>Start date</th>
                <th>End date</th>
                <th>Availability</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><input type="text" name="no.questions"></td>
                <td><input type="time" name="duration"></td>
                <td><input type="date" name="start" id="start-date"></td>
                <td><input type="date" name="end" id="end-date"></td>
                <td class="availability" id="availability-symbol"></td>
            </tr>
        </tbody>
    </table>
    
    <br><br>
    <button type= "submit" id=""><i class="tim-icons icon-cloud-upload-94"></i>    Upload all Challenges</button>
    </form>
    <br><br>
    <a href="{{ route('challenges') }}" class="btn btn-primary"> <i class="tim-icons icon-components"></i>   View Uploaded Challenges</a>

    </div>

    <script>
        const startDateInput = document.getElementById('start-date');
        const endDateInput = document.getElementById('end-date');
        const availabilityCell = document.querySelector('.availability');
        const currentDate = new Date();

        endDateInput.addEventListener('change', updateAvailability);
        startDateInput.addEventListener('change', updateAvailability);

        function updateAvailability() {
            const startDate = new Date(startDateInput.value);
            const endDate = new Date(endDateInput.value);

            if (startDate <= currentDate && currentDate <= endDate) {
                availabilityCell.textContent = '✓'; // Display tick symbol
            } else {
                availabilityCell.textContent = '✗'; // Display x symbol
            }
        }
    </script>
    <script>
  document.getElementById('addRow').addEventListener('click', function() {
    const tableBody = document.querySelector('tbody');
    const newRow = document.createElement('tr');
    newRow.classList.add('rows'); // Add the 'row' class to the new row

    // Create and append new cells to the row
    newRow.innerHTML = `
        <td><input type="text" name="no.questions"></td>
        <td><input type="time" name="duration"></td>
        <td><input type="date" name="start" id="start-date"></td>
        <td><input type="date" name="end" id="end-date"></td>
        <td class="availability" id="availability-symbol"></td>
    `;

    tableBody.appendChild(newRow);

    // Update the availability symbol for the new row
    const newStartDateInput = newRow.querySelector('#start-date');
    const newEndDateInput = newRow.querySelector('#end-date');
    const newAvailabilityCell = newRow.querySelector('.availability');

    // Function to update availability symbol
    function updateAvailability() {
        const currentDate = new Date();
        const startDate = new Date(newStartDateInput.value);
        const endDate = new Date(newEndDateInput.value);

        if (startDate <= currentDate && currentDate <= endDate) {
            newAvailabilityCell.textContent = '✓'; // Display tick symbol
        } else {
            newAvailabilityCell.textContent = '✗'; // Display x symbol
        }
    }

    // Attach event listeners to the new start and end date inputs
    newStartDateInput.addEventListener('change', updateAvailability);
    newEndDateInput.addEventListener('change', updateAvailability);
});
</script>
</body>
</html>
@endsection
