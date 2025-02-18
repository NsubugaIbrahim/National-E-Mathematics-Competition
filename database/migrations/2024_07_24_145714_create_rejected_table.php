<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('rejected', function (Blueprint $table) {
            $table->id('rejectedId');
            $table->string("username")->unique();
            $table->string("firstName");
            $table->string("lastName");
            $table->string("email")->unique();
            $table->string("password");
            $table->date("dateOfBirth");
            $table->string("schoolRegno");
            $table->string("imageFilePath")->nullable();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('rejected');
    }
};
