<template>
  <div class="container text-center">
    <h1 class="my-4">个人通讯录</h1>
    <contact-list
            :contacts="contacts"
            @add-contact="addContact"
            @edit-contact="editContact"
            @delete-contact="deleteContact"
    />
  </div>
</template>

<script>
  import ContactList from './components/ContactList.vue';

  export default {
    components: {
      ContactList
    },
    data() {
      return {
        contacts: []
      };
    },
    methods: {
      addContact(contact) {
        this.contacts.push(contact);
      },
      editContact(index, updatedContact) {
        this.$set(this.contacts, index, updatedContact);
      },
      deleteContact(index) {
        this.contacts.splice(index, 1);
      }
    }
  };
</script>

<style>
  .container {
    max-width: 600px; /* 限制最大宽度 */
    margin: auto; /* 居中 */
    padding: 20px;
    border: 1px solid #ddd; /* 添加边框 */
    border-radius: 10px; /* 圆角边框 */
    background-color: #f9f9f9; /* 背景色 */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 阴影效果 */
  }
</style>
