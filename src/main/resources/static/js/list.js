'use strict';
const handleDelete = async (id, endpoint) => {
    try {
      const base_url = window.location.origin;
      if (confirm("Bạn có chắc chắn muốn xóa (Điều này sẽ xoá hết tất cả mọi thứ liên quan đến trường này)?")) {
        const response = await fetch(`${base_url}${endpoint === "" ? "" : endpoint}/${id}`, {
          method: 'DELETE',
          headers: {
              'Content-Type': 'application/json'
          }
        });

        if (response.ok) {
          window.location.reload();
        } else {
          alert("Xóa không thành công. Vui lòng thử lại.");
        }
      }
    } catch (error) {
      alert('Đã xảy ra lỗi!');
    }
}