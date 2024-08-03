<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\Request;

class SchoolModel extends Model
{
    use HasFactory;

    protected $table = 'school';

    static public function getSingle($id)
    {
        return self::where('id', $id)->where('is_delete', 0)->first();
    }

    static public function getRecord()
    {
        $return = self::select('school.*', 'users.name as created_name')
            ->join('users', 'users.id', '=', 'school.created_by');

        if (!empty(Request::get('name'))) {
            $return = $return->where('school.name', 'like', '%' . Request::get('name') . '%');
        }

        if (!empty(Request::get('district'))) {
            $return = $return->where('school.district', 'like', '%' . Request::get('district') . '%');
        }

        if (!empty(Request::get('representative_name'))) {
            $return = $return->where('school.representative_name', 'like', '%' . Request::get('representative_name') . '%');
        }

        $return = $return->where('school.is_delete', 0)
            ->orderBy('school.id', 'desc')
            ->paginate(2);
        return $return;
    }

    static public function getExam()
    {
        $return = self::select('school.*')
            ->join('users', 'users.id', '=', 'school.created_by')
            ->where('school.is_delete', 0)
            ->orderBy('school.name', 'asc')
            ->get();
        return $return;
    }
}
