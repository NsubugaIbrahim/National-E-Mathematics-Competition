@extends('layouts.app', ['pageSlug' => 'dashboard'])

@section('content')

<!DOCTYPE html>
<html>
<head>
    <title>Schools Added Over Time</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<div class="row">
        <div class="col-12">
            <div class="card card-chart">
            <div class="card-body">
    <div style="width: 75%; margin: auto;">
        <canvas id="schoolsChart"></canvas>
    </div>
    </div>
    </div>
</div>
</div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            var ctx = document.getElementById('schoolsChart').getContext('2d');
            var schools = @json($schools);
            var labels = schools.map(school => {
                var year = school.week.toString().substring(0, 4);
                var week = school.week.toString().substring(4);
                return `Week ${week}, ${year}`;
            });
            var data = schools.map(school => school.count);

            var chart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Number of Schools Added',
                        data: data,
                        borderColor: 'rgba(75, 192, 192, 1)',
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        fill: true,
                        tension: 0.1
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        x: {
                            title: {
                                display: true,
                                text: 'Week'
                            }
                        },
                        y: {
                            beginAtZero: true,
                            ticks: {
                                stepSize: 1
                            },
                            title: {
                                display: true,
                                text: 'Number of Schools'
                            }
                        }
                    }
                }
            });
        });
    </script>


</body>
</html>


@endsection

