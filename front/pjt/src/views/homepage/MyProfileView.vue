<template>
  <div>
    <v-row justify="center">
      <v-dialog
        v-model="myProfileDialog"
        persistent
        max-width="500px"
      >
        <v-card
          class="mx-auto"
        >
          <div
            align="center"
          >
            <div
              
              height="200px"
            >
              <v-toolbar-title class="text-h6 grey--text pl-0 mx-5">
                My Profile 💜
              </v-toolbar-title>
              <!-- <v-avatar
                class="mt-3" 
                size="70"
              >
                <v-img
                  src="@/assets/image/profile.png" 
                  alt=""
                />
              </v-avatar> -->
   
              <div class="mx-auto text-center">
                <!-- <v-avatar
                  color="brown"
                >
                  <span class="white--text text-h5">{{ newCredentials.username }}</span>
                </v-avatar> -->
                <v-avatar
                  color="grey darken-3"
                  size="70"
                  class="my-3"
                >
                  <v-img
                    class="elevation-6"
                    alt=""
                    src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
                  />
                </v-avatar>

                <h3 bold>
                  {{ newCredentials.username }}님
                </h3>
                <p class="text-caption mt-1">
                  {{ newCredentials.email }}
                </p>
              
                <v-spacer />
              </div>
            </div>
          </div>
          <v-form
            v-model="valid"
            style="background-color:rgba(178,216,216, 0.5)"
            @submit.prevent="updateProfile(newCredentials)"
          >
            <v-container>
              <v-row>
                <v-col
                  cols="6"
                >
                  <v-text-field
                    v-model="newCredentials.username"
                    :rules="nameRules"
                    :counter="10"
                    label="이름"
                    color="blue-grey lighten-2"
                    required
                  />
                </v-col>
                <v-col
                  cols="6"
                >
                  <v-text-field
                  
                    v-model="newCredentials.department"
                    :rules="nameRules"
                    :counter="10"
                    label="부서"
                    color="blue-grey lighten-2"
                    required
                  />
                </v-col>
                
                <v-col
                  cols="6"
                >
                  <v-text-field
                    v-model="newCredentials.position"
                    :rules="nameRules"
                    :counter="10"
                    label="직책"
                    color="blue-grey lighten-2"
                    required
                  />
                </v-col>
                <v-col
                  cols="6"
                >
                  <v-text-field
                    v-model="newCredentials.email"               
                    :rules="idRules"
                    :counter="20"
                    readonly
                    label="email"
                    color="blue-grey lighten-2"
                    required
                  />
                </v-col>          
                <v-card-actions>
                  <v-spacer />
                  <v-btn
                    color="teal"
                    text
                    @click="openMyprofile"
                  >
                    닫기
                  </v-btn>
                  <div class="text-center">
                    <v-btn
                      type="submit" 
                      
                      :loading="dialog2"
                      class="mr-4 my-4 white--text"
                      :disabled="!valid"
                      color="teal"
                      @click="[alert(),myProfileDialog= false, openMyprofile]"
                    >
                      저장
                    </v-btn>
                    <v-dialog
                      v-model="dialog2"
                      hide-overlay
                      persistent
                      width="300"
                    >
                      <v-card
                        color="gray"
                        dark
                      >
                        <v-card-text>
                          Please stand by
                          <v-progress-linear
                            indeterminate
                            color="white"
                            class="mb-0"
                          />
                        </v-card-text>
                      </v-card>
                    </v-dialog>
                  </div>
                </v-card-actions>
              </v-row>
            </v-container>
          </v-form>
          <!--           
          <small>*indicates required field</small>
          <div
            id="imgFileUploadInsertWrapper"
            class="form-group mt-3 mb-3"
            align="left"
          >
            <v-form
              enctype="multipart/form-data"
              @submit.prevent="[onSuccess(files),updateProfileImage(files)]"
            >
              <v-file-input
                id="inputFileUploadInsert"
                v-model="files"
                placeholder="Upload your documents"
                label="File input"
                multiple
                prepend-icon="mdi-paperclip"
                color="teal lighten-1"
              >
                <template #selection="{ text }">
                  <v-chip
                    small
                    label
                    color="teal lighten-1"
                  >
                    {{ text }}
                  </v-chip>
                </template>
              </v-file-input>
              <v-btn
                class="mr-4 my-4"
                type="submit"
                :disabled="!valid"
                :color="primary"
              >
                save
              </v-btn>
            </v-form>
          </div> -->
        </v-card>
      </v-dialog>
    </v-row>
  </div>
</template>

<script>

import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'MyProfileView',
  data() {
    return {
      isShowBtn: false,
      myprofileDialog: false,
      dialog2: false,
      valid: true,
      isDisabled: true,
      newCredentials: {
        email: JSON.parse(localStorage.newCredentials).email,
        username: JSON.parse(localStorage.newCredentials).username,
        department: JSON.parse(localStorage.newCredentials).department,
        position: JSON.parse(localStorage.newCredentials).position,
        },
      profileImageUrl: '',
      files: [],
      nameRules: [
        (v) => !!v || "이름을 입력하세요.",
        (v) => v.length <= 10 || "10글자 이내로 작성해주세요.",
      ],
      departmentRules: [
        (v) => !!v || "소속을 입력하세요.",
        (v) => v.length <= 30 || "30글자 이내로 작성해주세요.",
      ],
      positionRules: [
        (v) => !!v || "직책을 입력하세요.",
        (v) => v.length <= 30 || "30글자 이내로 작성해주세요.",
      ],
      idRules: [
        (v) => !!v || "아이디를 입력하세요.",
        (v) => /.+@.+/.test(v) || "이메일 형식이 아닙니다.",
      ],
    }},
    
    computed: {
      ...mapGetters(['myProfileDialog']),
      profileImg(){
        if( this.$store.state.profileImageUrl == undefined) {
          return '@/assets/Profile.png';
        }else{
          return '/' + this.$store.state.ImageUrl;
        }

     },
     
    },
    watch: {
      dialog2 (val) {
        if (!val) return
        setTimeout(() => (this.dialog2 = false), 4000)
      },
    },
    methods: {
      ...mapActions(['updateProfile','openMyprofile']),
      updateProfileImage(files) {
          const updateImage = files
          const headers = {
          "BAERER-TOKEN": this.$store.state.Token,
        }
          this.$store.dispatch('updateProfileImage', updateImage)
      },
      alert() {
        this.$store.dispatch('updateAlert', {
          alert: true,
          type: 'success',
          title: '수정 완료',
          text: '수정 완료',
        })
      },
      onSuccess(files) {
        console.log(files)
        const formData = new FormData(this.$refs.files)
        formData.append('file', files.name)

        this.updateProfileImage({ formData: formData, imgName: files.name })
        .then(res => console.log(res) )
      },
    }
}
</script>

<style>

</style>
