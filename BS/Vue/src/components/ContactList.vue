<template>
    <div>
        <form @submit.prevent="handleSubmit" class="mb-4">
            <div class="form-group">
                <input v-model="newContact.name" class="form-control" placeholder="姓名" required />
            </div>
            <div class="form-group">
                <input v-model="newContact.address" class="form-control" placeholder="住址" required />
            </div>
            <div class="form-group">
                <input v-model="newContact.phone" class="form-control" placeholder="电话" required />
            </div>
            <button type="submit" class="btn btn-primary btn-lg">添加联系人</button>
        </form>

        <h3 class="my-4">联系人列表</h3>
        <ul class="list-group">
            <li v-for="(contact, index) in contacts" :key="index" class="list-group-item d-flex justify-content-between align-items-center">
                <div>
                    <strong>{{ contact.name }}</strong><br>
                    <small>{{ contact.address }} - {{ contact.phone }}</small>
                </div>
                <div>
                    <button @click="editContact(index)" class="btn btn-warning btn-sm mr-2">编辑</button>
                    <button @click="deleteContact(index)" class="btn btn-danger btn-sm">删除</button>
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
    export default {
        props: ['contacts'],
        data() {
            return {
                newContact: {
                    name: '',
                    address: '',
                    phone: ''
                }
            };
        },
        methods: {
            handleSubmit() {
                this.$emit('add-contact', { ...this.newContact });
                this.newContact.name = '';
                this.newContact.address = '';
                this.newContact.phone = '';
            },
            editContact(index) {
                const updatedContact = { ...this.newContact };
                this.$emit('edit-contact', index, updatedContact);
                this.newContact.name = '';
                this.newContact.address = '';
                this.newContact.phone = '';
            },
            deleteContact(index) {
                this.$emit('delete-contact', index);
            }
        }
    };
</script>

<style>
    .list-group-item {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
</style>
