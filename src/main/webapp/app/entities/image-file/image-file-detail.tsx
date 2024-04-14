import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './image-file.reducer';

export const ImageFileDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const imageFileEntity = useAppSelector(state => state.imageFile.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="imageFileDetailsHeading">
          <Translate contentKey="xploraAdminApp.imageFile.detail.title">ImageFile</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{imageFileEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="xploraAdminApp.imageFile.code">Code</Translate>
            </span>
          </dt>
          <dd>{imageFileEntity.code}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="xploraAdminApp.imageFile.title">Title</Translate>
            </span>
          </dt>
          <dd>{imageFileEntity.title}</dd>
          <dt>
            <span id="alt">
              <Translate contentKey="xploraAdminApp.imageFile.alt">Alt</Translate>
            </span>
          </dt>
          <dd>{imageFileEntity.alt}</dd>
          <dt>
            <span id="filename">
              <Translate contentKey="xploraAdminApp.imageFile.filename">Filename</Translate>
            </span>
          </dt>
          <dd>{imageFileEntity.filename}</dd>
          <dt>
            <span id="data">
              <Translate contentKey="xploraAdminApp.imageFile.data">Data</Translate>
            </span>
          </dt>
          <dd>
            {imageFileEntity.data ? (
              <div>
                {imageFileEntity.dataContentType ? (
                  <a onClick={openFile(imageFileEntity.dataContentType, imageFileEntity.data)}>
                    <img src={`data:${imageFileEntity.dataContentType};base64,${imageFileEntity.data}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {imageFileEntity.dataContentType}, {byteSize(imageFileEntity.data)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="xploraAdminApp.imageFile.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {imageFileEntity.createdDate ? (
              <TextFormat value={imageFileEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.imageFile.createdBy">Created By</Translate>
          </dt>
          <dd>{imageFileEntity.createdBy ? imageFileEntity.createdBy.login : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.imageFile.destination">Destination</Translate>
          </dt>
          <dd>{imageFileEntity.destination ? imageFileEntity.destination.code : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.imageFile.tour">Tour</Translate>
          </dt>
          <dd>{imageFileEntity.tour ? imageFileEntity.tour.code : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.imageFile.tourCategory">Tour Category</Translate>
          </dt>
          <dd>{imageFileEntity.tourCategory ? imageFileEntity.tourCategory.code : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.imageFile.place">Place</Translate>
          </dt>
          <dd>{imageFileEntity.place ? imageFileEntity.place.code : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.imageFile.placeCategory">Place Category</Translate>
          </dt>
          <dd>{imageFileEntity.placeCategory ? imageFileEntity.placeCategory.code : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.imageFile.vehicle">Vehicle</Translate>
          </dt>
          <dd>{imageFileEntity.vehicle ? imageFileEntity.vehicle.plate : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.imageFile.driver">Driver</Translate>
          </dt>
          <dd>{imageFileEntity.driver ? imageFileEntity.driver.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/image-file" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/image-file/${imageFileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ImageFileDetail;
