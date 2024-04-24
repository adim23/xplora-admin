import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILanguage } from 'app/shared/model/language.model';
import { getEntities as getLanguages } from 'app/entities/language/language.reducer';
import { ITourContent } from 'app/shared/model/tour-content.model';
import { getEntity, updateEntity, createEntity, reset } from './tour-content.reducer';

export const TourContentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const languages = useAppSelector(state => state.language.entities);
  const tourContentEntity = useAppSelector(state => state.tourContent.entity);
  const loading = useAppSelector(state => state.tourContent.loading);
  const updating = useAppSelector(state => state.tourContent.updating);
  const updateSuccess = useAppSelector(state => state.tourContent.updateSuccess);

  const handleClose = () => {
    navigate('/tour-content' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLanguages({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...tourContentEntity,
      ...values,
      language: languages.find(it => it.id.toString() === values.language?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...tourContentEntity,
          language: tourContentEntity?.language?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="xploraAdminApp.tourContent.home.createOrEditLabel" data-cy="TourContentCreateUpdateHeading">
            <Translate contentKey="xploraAdminApp.tourContent.home.createOrEditLabel">Create or edit a TourContent</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="tour-content-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('xploraAdminApp.tourContent.code')}
                id="tour-content-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourContent.title')}
                id="tour-content-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourContent.shortDescription')}
                id="tour-content-shortDescription"
                name="shortDescription"
                data-cy="shortDescription"
                type="textarea"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourContent.data')}
                id="tour-content-data"
                name="data"
                data-cy="data"
                type="textarea"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourContent.meta')}
                id="tour-content-meta"
                name="meta"
                data-cy="meta"
                type="textarea"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourContent.cancellation')}
                id="tour-content-cancellation"
                name="cancellation"
                data-cy="cancellation"
                type="text"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourContent.activityPath')}
                id="tour-content-activityPath"
                name="activityPath"
                data-cy="activityPath"
                type="textarea"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourContent.atAGlance')}
                id="tour-content-atAGlance"
                name="atAGlance"
                data-cy="atAGlance"
                type="textarea"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourContent.inDetail')}
                id="tour-content-inDetail"
                name="inDetail"
                data-cy="inDetail"
                type="textarea"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourContent.whatIsIncluded')}
                id="tour-content-whatIsIncluded"
                name="whatIsIncluded"
                data-cy="whatIsIncluded"
                type="textarea"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourContent.youCanAdd')}
                id="tour-content-youCanAdd"
                name="youCanAdd"
                data-cy="youCanAdd"
                type="textarea"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourContent.importantInformation')}
                id="tour-content-importantInformation"
                name="importantInformation"
                data-cy="importantInformation"
                type="textarea"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourContent.extraInfo')}
                id="tour-content-extraInfo"
                name="extraInfo"
                data-cy="extraInfo"
                type="textarea"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tourContent.createdDate')}
                id="tour-content-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="date"
              />
              <ValidatedField
                id="tour-content-language"
                name="language"
                data-cy="language"
                label={translate('xploraAdminApp.tourContent.language')}
                type="select"
                required
              >
                <option value="" key="0" />
                {languages
                  ? languages.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tour-content" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TourContentUpdate;
