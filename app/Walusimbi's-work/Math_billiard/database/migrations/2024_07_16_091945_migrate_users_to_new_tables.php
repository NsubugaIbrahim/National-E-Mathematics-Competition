<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;
use App\Models\User;
use App\Models\Admin;
use App\Models\SchoolRepresentative;
use App\Models\Student;

class MigrateUsersToNewTables extends Migration
{
    public function up()
    {
        $users = User::all();

        foreach ($users as $user) {
            switch ($user->user_type) {
                case 1: // Admin
                    Admin::create([
                        'name' => $user->name,
                        'email' => $user->email,
                        'password' => $user->password,
                        // Add other fields as necessary
                    ]);
                    break;
                case 2: // School Representative
                    SchoolRepresentative::create([
                        'name' => $user->name,
                        'email' => $user->email,
                        'password' => $user->password,
                        // Add other fields as necessary
                    ]);
                    break;
                case 3: // Student
                    Student::create([
                        'name' => $user->name,
                        'email' => $user->email,
                        'password' => $user->password,
                        // Add other fields as necessary
                    ]);
                    break;
                // Add more cases as necessary
            }
        }
    }

    public function down()
    {
        Admin::truncate();
        SchoolRepresentative::truncate();
        Student::truncate();
    }
}
