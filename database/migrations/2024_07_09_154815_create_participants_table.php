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
        Schema::create('participants', function (Blueprint $table) {
            $table->id("participantId");
            $table->string("challengeId");
            $table->string("resultId");
            $table->string("username")->unique();
            $table->string("firstName");
            $table->string("lastName");
            $table->string("email")->unique();
            $table->date("dateOfBirth");
            $table->string("schoolRegno");
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('participants');
    }
};
